package com.bytemaniak.mcquake3.entity;

import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PortalEntity extends PropEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final static TrackedData<Byte> FACING = DataTracker.registerData(PortalEntity.class, TrackedDataHandlerRegistry.BYTE);

    public PortalEntity(EntityType<?> type, World world) {
        super(type, world, Blocks.PORTAL_ITEM);
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(FACING, (byte) Direction.NORTH.getId());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        setFacing(Direction.byId(nbt.getByte("facing")));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putByte("facing", dataTracker.get(FACING));
    }

    public void setFacing(Direction direction) {
        dataTracker.set(FACING, (byte)direction.getId());
        setYaw(direction.getHorizontal() * 90);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!player.getStackInHand(hand).isOf(Weapons.TOOL)) return ActionResult.FAIL;
        else {
            if (player.isSneaking()) {
                Direction facing = Direction.byId(dataTracker.get(FACING));
                facing = facing.rotateYClockwise();
                setFacing(facing);
            }
            return ActionResult.SUCCESS;
        }
    }

    public void teleportEntity(Entity entity) {
        entity.teleport(getX(), getY()+50, getZ());
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!getWorld().isClient) {
            Box playerBox = player.getBoundingBox().expand(.1f);
            if (playerBox.intersects(getBoundingBox())) {
                teleportEntity(player);
                getWorld().playSound(player, getBlockPos(), Sounds.TELEPORT_IN, SoundCategory.NEUTRAL);
                getWorld().playSoundFromEntity(null, player, Sounds.TELEPORT_OUT, SoundCategory.NEUTRAL, 1, 1);
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }
}
