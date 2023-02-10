package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.cs.CSMessages;
import com.bytemaniak.mcuake.entity.MCuakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class SimpleProjectile extends ExplosiveProjectileEntity {
    private int quakeDamageAmount, mcDamageAmount;
    private String projectileName;
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
        this.projectileName = projectileName;
        this.lifetimeInTicks = lifetimeInTicks;
        this.initTick = world.getTime();
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.getTime() - initTick > lifetimeInTicks) {
            this.discard();
        }
    }

    private void doDamage(Entity entity) {
        if (!world.isClient) {
            ProjectileDamageSource damageSource = new ProjectileDamageSource("mcuake."+projectileName, this, getOwner());
            if (entity instanceof PlayerEntity playerEntity && playerEntity.isAlive()) {
                MCuakePlayer quakePlayer = (MCuakePlayer) playerEntity;
                if (quakePlayer.isInQuakeMode()) {
                    quakePlayer.takeDamage(quakeDamageAmount, damageSource);
                    if (this.getOwner() != null) {
                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeInt(quakePlayer.getQuakeHealth());
                        buf.writeInt(quakePlayer.getQuakeArmor());
                        ServerPlayNetworking.send((ServerPlayerEntity) playerEntity, CSMessages.PLAYER_STATS_UPDATE, buf);
                        ServerPlayNetworking.send((ServerPlayerEntity) this.getOwner(), CSMessages.DEALT_DAMAGE, PacketByteBufs.empty());
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
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (!this.world.isClient) {
            doDamage(entity);
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
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
