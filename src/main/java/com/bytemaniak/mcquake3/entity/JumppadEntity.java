package com.bytemaniak.mcquake3.entity;

import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.screen.JumppadScreenHandler;
import com.bytemaniak.mecha.MultiCollidable;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
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

public class JumppadEntity extends PropEntity implements GeoEntity, ExtendedScreenHandlerFactory, MultiCollidable {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final static TrackedData<Byte> POWER = DataTracker.registerData(JumppadEntity.class, TrackedDataHandlerRegistry.BYTE);
    private final static TrackedData<Byte> FACING = DataTracker.registerData(JumppadEntity.class, TrackedDataHandlerRegistry.BYTE);

    private static final int JUMPPAD_BOOST_SOUND_TICKS_COOLDOWN = 10;
    public long lastTick = 0;

    public JumppadEntity(EntityType<JumppadEntity> entityType, World world) {
        super(entityType, world, Blocks.JUMPPAD_ITEM);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new JumppadScreenHandler(syncId, inv, this);
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(POWER, (byte)0);
        dataTracker.startTracking(FACING, (byte)Direction.UP.getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        dataTracker.set(POWER, nbt.getByte("power"));
        setFacing(Direction.byId(nbt.getByte("facing")));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putByte("power", dataTracker.get(POWER));
        nbt.putByte("facing", dataTracker.get(FACING));
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeByte(dataTracker.get(POWER));
    }

    public List<VoxelShape> getColliders() {
        List<VoxelShape> colliders = new ArrayList<>();
        BlockPos pos = getBlockPos();
        final double hoff = 1.875/6, voff = 1.25/6;
        double x = pos.getX()-.875/2;
        double y = pos.getY();
        double z = pos.getZ()-.875/2;
        switch (getFacing()) {
            case UP -> colliders.add(VoxelShapes.cuboid(x, y, z, x + 1.875, y + voff, z + 1.875));
            case NORTH -> {
                for (int i = 0; i < 6; i++) {
                    colliders.add(VoxelShapes.cuboid(x, y, z, x+1.875, y + voff, z+hoff));
                    z += hoff;
                    y += voff;
                }
            }
            case EAST -> {
                y += 5*1.25/6;
                for (int i = 0; i < 6; i++) {
                    colliders.add(VoxelShapes.cuboid(x, y, z, x + hoff, y + voff, z+1.875));
                    x += hoff;
                    y -= voff;
                }
            }
            case SOUTH -> {
                y += 5*1.25/6;
                for (int i = 0; i < 6; i++) {
                    colliders.add(VoxelShapes.cuboid(x, y, z, x+1.875, y + voff, z+hoff));
                    z += hoff;
                    y -= voff;
                }
            }
            case WEST -> {
                for (int i = 0; i < 6; i++) {
                    colliders.add(VoxelShapes.cuboid(x, y, z, x + hoff, y + voff, z + 1.875));
                    x += hoff;
                    y += voff;
                }
            }
        }

        return colliders;
    }

    public Vec3d getVelocityVector() {
        Vec3d v = Vec3d.fromPolar(getPitch() - 90, getYaw());
        v = v.multiply(getPower());
        v = v.multiply(1, 1 / Math.sqrt(v.y), 1);
        return v;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (getPower() > 0) {
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
                World world = getWorld();
                if (world.getTime() - lastTick > JUMPPAD_BOOST_SOUND_TICKS_COOLDOWN) {
                    lastTick = getWorld().getTime();
                    if (world.isClient) {
                        Vec3d v = getVelocityVector();
                        player.setOnGround(false);
                        player.addVelocity(v);
                        player.velocityModified = true;
                        world.playSound(player, getBlockPos(), Sounds.JUMPPAD_BOOST, SoundCategory.BLOCKS, 1, 1);

                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeBlockPos(getBlockPos());
                        ClientPlayNetworking.send(Packets.JUMPPAD_SOUND, buf);
                    }
                }
            }
        }
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

    public void updatePower(byte power) { dataTracker.set(POWER, power); }
    public byte getPower() { return dataTracker.get(POWER); }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }
}