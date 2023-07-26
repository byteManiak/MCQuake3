package com.bytemaniak.mcquake3.entity.projectile;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import com.bytemaniak.mcquake3.registry.DamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.world.World;

public abstract class SimpleProjectile extends ExplosiveProjectileEntity {
    private int quakeDamageAmount, mcDamageAmount;
    protected String damageType;
    protected long lifetimeInTicks;
    protected long initTick;

    protected void initDataTracker() {}
    public SimpleProjectile(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
        initTick = world.getTime();
    }

    public SimpleProjectile(EntityType<? extends ExplosiveProjectileEntity> entityType, World world,
                            int quakeDamageAmount, int mcDamageAmount,
                            String projectileName, int lifetimeInTicks) {
        this(entityType, world);
        this.quakeDamageAmount = quakeDamageAmount;
        this.mcDamageAmount = mcDamageAmount;
        this.damageType = projectileName;
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
            DamageSource damageSource = new DamageSources.QuakeDamageSource(damageType, getOwner());

            if (entity.isAlive() && entity instanceof QuakePlayer quakePlayer && quakePlayer.isInQuakeMode()) {
                entity.damage(damageSource, quakeDamageAmount);
            } else {
                entity.damage(damageSource, mcDamageAmount);
            }

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
