package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.MCuake;
import com.bytemaniak.mcuake.cs.CSMessages;
import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PlasmaBall extends ExplosiveProjectileEntity {
    private int PLASMABALL_DAMAGE = 10;

    public PlasmaBall(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) { super(entityType, world); }

    public PlasmaBall(World world) { super(MCuake.PLASMA_BALL, world); }

    private void doDamage(Entity entity)
    {
        if (!world.isClient) {
            if (entity instanceof PlayerEntity playerEntity && playerEntity.isAlive()) {
                MCuakePlayer quakePlayer = (MCuakePlayer) playerEntity;
                quakePlayer.takeDamage(PLASMABALL_DAMAGE, new ProjectileDamageSource("mcuake.plasmagun", this, this.getOwner()));
                if (this.getOwner() != null) {
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeInt(quakePlayer.getQuakeHealth());
                    buf.writeInt(quakePlayer.getQuakeArmor());
                    ServerPlayNetworking.send((ServerPlayerEntity) playerEntity, CSMessages.PLAYER_STATS_UPDATE, buf);
                    ServerPlayNetworking.send((ServerPlayerEntity) this.getOwner(), CSMessages.DEALT_DAMAGE, PacketByteBufs.empty());
                }
            } else {
                entity.damage(DamageSources.PLASMAGUN_DAMAGE, PLASMABALL_DAMAGE / 4.f);
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

    @Override
    protected boolean isBurning() { return false; }

    @Override
    // Don't slow down the projectile gradually
    protected float getDrag() { return 1; }
}
