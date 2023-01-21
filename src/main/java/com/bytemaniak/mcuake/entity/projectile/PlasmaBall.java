package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.MCuake;
import com.bytemaniak.mcuake.registry.DamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PlasmaBall extends ExplosiveProjectileEntity {
    private float PLASMABALL_DAMAGE = 10;

    public PlasmaBall(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public PlasmaBall(World world)
    {
        super(MCuake.PLASMA_BALL, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (!this.world.isClient) {
            Entity entity = entityHitResult.getEntity();
            if (entity instanceof PlayerEntity playerEntity && playerEntity.isAlive()) {
                this.getOwner().playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1, .5f);
            } else {
                entity.damage(DamageSources.PLASMAGUN_DAMAGE, PLASMABALL_DAMAGE / 4);
            }

            this.kill();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient)
        {
           this.kill();
        }
    }

    @Override
    public boolean canHit() {
        // Player cannot hit the projectile to return to sender
        return false;
    }

    @Override
    public boolean isTouchingWater() {
        // Don't slow the projectile if in water
        return false;
    }

    @Override
    protected boolean isBurning() {
        return false;
    }

    @Override
    protected float getDrag() {
        // Don't slow down the projectile gradually
        return 1;
    }
}
