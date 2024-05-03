package com.bytemaniak.mcquake3.entity;

import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.screen.JumppadScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JumppadEntity extends Entity implements GeoEntity, ExtendedScreenHandlerFactory {
    public static final float JUMPPAD_ENTITY_POWER_MAX = 9.5f;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final static TrackedData<Float> POWER = DataTracker.registerData(JumppadEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private final static TrackedData<Byte> FACING = DataTracker.registerData(JumppadEntity.class, TrackedDataHandlerRegistry.BYTE);

    private static final int JUMPPAD_BOOST_SOUND_TICKS_COOLDOWN = 10;
    public long lastTick = 0;

    private UUID lastPlayerUser;
    private JumppadScreenHandler lastScreen;

    public JumppadEntity(EntityType<JumppadEntity> entityType, World world) {
        super(entityType, world);
        lastPlayerUser = UUID.randomUUID();
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        lastScreen = new JumppadScreenHandler(syncId, inv, this);
        return lastScreen;
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(POWER, 0.f);
        dataTracker.startTracking(FACING, (byte)Direction.UP.getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        dataTracker.set(POWER, nbt.getFloat("power"));
        setFacing(Direction.byId(nbt.getByte("facing")));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putFloat("power", dataTracker.get(POWER));
        nbt.putByte("facing", dataTracker.get(FACING));
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeFloat(dataTracker.get(POWER));
    }

    @Override
    public ItemStack getPickBlockStack() { return new ItemStack(Blocks.JUMPPAD_ITEM); }

    @Override
    public boolean canHit() { return !isRemoved(); }

    public List<VoxelShape> getColliders() {
        List<VoxelShape> colliders = new ArrayList<>();
        BlockPos pos = getBlockPos();
        final double hoff = 1.875/6, voff = 1.25/6;
        double x = pos.getX()-.875/2;
        double y = pos.getY();
        double z = pos.getZ()-.875/2;
        switch (getFacing()) {
            case UP -> colliders.add(VoxelShapes.cuboid(x, y, z, x + 1.875, y + voff, z + 1.875));
            case EAST -> {
                for (int i = 0; i < 6; i++) {
                    colliders.add(VoxelShapes.cuboid(x, y, z, x+1.875, y + voff, z+hoff));
                    z += hoff;
                    y += voff;
                }
            }
            case NORTH -> {
                y += 5*1.25/6;
                for (int i = 0; i < 6; i++) {
                    colliders.add(VoxelShapes.cuboid(x, y, z, x + hoff, y + voff, z+1.875));
                    x += hoff;
                    y -= voff;
                }
            }
            case WEST -> {
                y += 5*1.25/6;
                for (int i = 0; i < 6; i++) {
                    colliders.add(VoxelShapes.cuboid(x, y, z, x+1.875, y + voff, z+hoff));
                    z += hoff;
                    y -= voff;
                }
            }
            case SOUTH -> {
                for (int i = 0; i < 6; i++) {
                    colliders.add(VoxelShapes.cuboid(x, y, z, x + hoff, y + voff, z+1.875));
                    x += hoff;
                    y += voff;
                }
            }
        }

        return colliders;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (getWorld().isClient && getPower() > 0) {
            Box playerBox = player.getBoundingBox().expand(.1f);
            Box box = null;
            boolean intersectsAny = false;
            for (VoxelShape shape : getColliders()) {
                box = shape.getBoundingBox();
                if (playerBox.intersects(box)) {
                    intersectsAny = true;
                    break;
                }
            }
            if (intersectsAny && playerBox.minY >= box.minY) {
                Vec3d v = Vec3d.fromPolar(getPitch()-90, 90-getYaw());
                v = v.multiply(getPower());
                v = v.multiply(1, 1/Math.sqrt(v.y), 1);
                player.setOnGround(false);
                player.addVelocity(v);
                player.velocityModified = true;
                if (getWorld().getTime() - lastTick > JUMPPAD_BOOST_SOUND_TICKS_COOLDOWN) {
                    getWorld().playSound(null, getBlockPos(), Sounds.JUMPPAD_BOOST, SoundCategory.BLOCKS, 1, 1);
                    lastTick = getWorld().getTime();
                }
            }
        }
    }

    private void onBreak() {
        ItemStack itemStack = new ItemStack(Blocks.JUMPPAD_ITEM);
        if (hasCustomName()) itemStack.setCustomName(this.getCustomName());
        dropStack(itemStack);
        kill();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (getWorld().isClient) return false;
        if (source.isSourceCreativePlayer() ||
            (source.isIn(DamageTypeTags.IS_EXPLOSION) && !source.getName().contains("mcquake3"))) {
            onBreak();
            return true;
        }
        return false;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!player.getStackInHand(hand).isOf(Weapons.TOOL)) return ActionResult.FAIL;
        else {
            if (player.isSneaking()) {
                Direction facing = Direction.byId(dataTracker.get(FACING));
                if (facing == Direction.UP)
                    facing = Direction.NORTH;
                else if (facing == Direction.WEST)
                    facing = Direction.UP;
                else facing = facing.rotateYClockwise();
                setFacing(facing);
            }
            else player.openHandledScreen(this);
            return ActionResult.SUCCESS;
        }
    }

    public void setFacing(Direction direction) {
        dataTracker.set(FACING, (byte)direction.getId());
        if (direction != Direction.UP) {
            setPitch(35);
            setYaw(direction.getHorizontal() * 90);
        } else {
            setPitch(0);
            setYaw(0);
        }
    }
    public Direction getFacing() { return Direction.byId(dataTracker.get(FACING)); }

    public void updatePower(float power) { dataTracker.set(POWER, power); }
    public float getPower() { return dataTracker.get(POWER); }

    public PlayerEntity getLastPlayerUser() { return getWorld().getPlayerByUuid(lastPlayerUser); }

    public void setLastPlayerUser(PlayerEntity playerUser) { lastPlayerUser = playerUser.getUuid(); }

    public JumppadScreenHandler getLastScreen() { return lastScreen; }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }
}