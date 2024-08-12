package com.bytemaniak.mcquake3.entity.projectile;

import com.bytemaniak.mcquake3.entity.PortalEntity;
import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public abstract class SimpleProjectile extends ExplosiveProjectileEntity {
    private float damageAmount;
    protected RegistryKey<DamageType> damageType;
    protected long lifetimeInTicks;
    protected long initTick;

    public SimpleProjectile(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
        initTick = getWorld().getTime();
    }

    public SimpleProjectile(EntityType<? extends ExplosiveProjectileEntity> entityType, World world,
                            float damageAmount, RegistryKey<DamageType> damageType, int lifetimeInTicks) {
        this(entityType, world);
        this.damageAmount = damageAmount;
        this.damageType = damageType;
        this.lifetimeInTicks = lifetimeInTicks;
        this.initTick = getWorld().getTime();
    }

    protected void despawn() {
        discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (!getWorld().isClient && getWorld().getTime() - initTick > lifetimeInTicks) {
            despawn();
        }
    }

    public void setQuadDamage() {
        this.damageAmount *= 3;
    }

    protected void doDamage(Entity entity) {
        if (!getWorld().isClient) {
            DamageSource damageSource = Q3DamageSources.of(getWorld(), damageType, this, getOwner());
            entity.damage(damageSource, damageAmount);
            despawn();
        }
    }

    protected void onCollision2(HitResult hitResult) {
        if (!getWorld().isClient) despawn();
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHitResult = (EntityHitResult) hitResult;
            if (entityHitResult.getEntity() instanceof PortalEntity portalEntity) {
                double speed = getVelocity().length();
                portalEntity.teleportEntity(this);
                setVelocity(portalEntity.getTeleportFacingVector().multiply(speed));
                velocityDirty = true;
                return;
            }
        }
        super.onCollision(hitResult);
        onCollision2(hitResult);
    }

    @Override
    // Player cannot hit the projectile to return to sender
    public boolean canHit() { return false; }

    @Override
    // Don't slow the projectile if in water
    public boolean isTouchingWater() { return false; }

    @Override
    protected boolean isBurning() { return false; }

    @Override
    // Don't slow down the projectile gradually
    protected float getDrag() { return 1; }
}
