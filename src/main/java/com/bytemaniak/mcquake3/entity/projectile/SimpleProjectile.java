package com.bytemaniak.mcquake3.entity.projectile;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public abstract class SimpleProjectile extends ExplosiveProjectileEntity {
    private float damageAmount;
    protected RegistryKey<DamageType> damageType;
    protected long lifetimeInTicks;
    protected long initTick;

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
        discard();
    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isClient && world.getTime() - initTick > lifetimeInTicks) {
            despawn();
        }
    }

    public void setQuadDamage() {
        this.damageAmount *= 4;
    }

    protected void doDamage(Entity entity) {
        if (!world.isClient) {
            DamageSource damageSource = Q3DamageSources.of(world, damageType, this, getOwner());

            if (getOwner() != null && entity instanceof PlayerEntity)
                ServerPlayNetworking.send((ServerPlayerEntity) getOwner(), Packets.DEALT_DAMAGE, PacketByteBufs.empty());

            entity.damage(damageSource, damageAmount);
            despawn();
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
