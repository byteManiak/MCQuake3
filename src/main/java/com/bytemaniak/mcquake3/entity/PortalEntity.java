package com.bytemaniak.mcquake3.entity;

import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PortalEntity extends Entity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public PortalEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        Box playerBox = player.getBoundingBox().expand(.1f);
        if (playerBox.intersects(getBoundingBox())) {
            if (getWorld().isClient)
                player.setPosition(getPos().add(0, 50, 0));
            else {
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
