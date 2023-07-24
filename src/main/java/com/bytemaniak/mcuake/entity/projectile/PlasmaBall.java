package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Entities;
import com.bytemaniak.mcuake.registry.Sounds;
import com.bytemaniak.mcuake.sound.TrackedSound;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PlasmaBall extends SimpleProjectile {
    private static final int PLASMAGUN_QUAKE_DAMAGE = 20;
    private static final int PLASMAGUN_MC_DAMAGE = 4;

    public PlasmaBall(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, PLASMAGUN_QUAKE_DAMAGE, PLASMAGUN_MC_DAMAGE, DamageSources.PLASMAGUN_DAMAGE, 75);
    }

    public PlasmaBall(World world) { this(Entities.PLASMA_BALL, world); }

    @Environment(EnvType.CLIENT)
    private void playSound() {
        TrackedSound flyingSound = new TrackedSound(this, Sounds.PLASMABALL_FLYING);
        MinecraftClient.getInstance().getSoundManager().play(flyingSound);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        if (world.isClient) playSound();

        super.onSpawnPacket(packet);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (!this.world.isClient) {
            this.world.playSound(null, getBlockPos(), Sounds.PLASMABALL_HIT, SoundCategory.NEUTRAL);
            doDamage(entity);
        }
    }

    @Override
    public void onCollision(HitResult hitResult)
    {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.playSound(null, getBlockPos(), Sounds.PLASMABALL_HIT, SoundCategory.NEUTRAL);
            this.kill();
        }
    }
}
