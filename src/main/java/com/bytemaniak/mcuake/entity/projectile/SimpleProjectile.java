package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Packets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public abstract class SimpleProjectile extends ExplosiveProjectileEntity {
    private int quakeDamageAmount, mcDamageAmount;
    private String damageType;
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
            if (entity instanceof PlayerEntity playerEntity && playerEntity.isAlive()) {
                QuakePlayer quakePlayer = (QuakePlayer) playerEntity;
                if (quakePlayer.isInQuakeMode()) {
                    quakePlayer.takeDamage(quakeDamageAmount, damageSource);
                    if (this.getOwner() != null) {
                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeInt(quakePlayer.getQuakeHealth());
                        buf.writeInt(quakePlayer.getQuakeArmor());
                        ServerPlayNetworking.send((ServerPlayerEntity) this.getOwner(), Packets.DEALT_DAMAGE, PacketByteBufs.empty());
                    }
                } else {
                    entity.damage(damageSource, mcDamageAmount);
                }
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

    //@Override
    protected boolean isBurning() { return false; }

    //@Override
    // Don't slow down the projectile gradually
    protected float getDrag() { return 1; }
}
