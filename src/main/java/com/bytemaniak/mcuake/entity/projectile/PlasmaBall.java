package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.MCuake;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PlasmaBall extends ExplosiveProjectileEntity {
    private float PLASMABALL_DAMAGE = 4;

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
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), PLASMABALL_DAMAGE);
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
        return false;
    }

    @Override
    public boolean isTouchingWater() {
        return false;
    }

    @Override
    protected boolean isBurning() {
        return false;
    }

    @Override
    protected float getDrag() {
        return 1;
    }
}
