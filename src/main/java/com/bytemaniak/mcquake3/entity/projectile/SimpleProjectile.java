package com.bytemaniak.mcquake3.entity.projectile;

import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public abstract class SimpleProjectile extends ExplosiveProjectileEntity {
    private float damageAmount;
    protected RegistryKey<DamageType> damageType;
    protected long lifetimeInTicks;
    protected long initTick;

    protected void initDataTracker() {}
    public SimpleProjectile(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
        initTick = world.getTime();
    }

    public SimpleProjectile(EntityType<? extends ExplosiveProjectileEntity> entityType, World world,
                            float damageAmount, RegistryKey<DamageType> damageType, int lifetimeInTicks) {
        this(entityType, world);
        this.damageAmount = damageAmount;
        this.damageType = damageType;
        this.lifetimeInTicks = lifetimeInTicks;
        this.initTick = world.getTime();
    }

    protected void despawn() {
        this.discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.getTime() - initTick > lifetimeInTicks) {
            this.despawn();
        }
    }

    protected void doDamage(Entity entity) {
        if (!world.isClient) {
            DamageSource damageSource = Q3DamageSources.of(world, damageType, this, getOwner());
            entity.damage(damageSource, damageAmount);
            this.kill();
        }
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
