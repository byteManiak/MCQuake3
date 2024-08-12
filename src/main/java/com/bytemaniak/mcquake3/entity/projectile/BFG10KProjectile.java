package com.bytemaniak.mcquake3.entity.projectile;

import com.bytemaniak.mcquake3.registry.Entities;
import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.sound.TrackedSound;
import com.bytemaniak.mcquake3.util.MiscUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class BFG10KProjectile extends SimpleProjectile {
    private static final float BFG10K_DAMAGE = MiscUtils.toMCDamage(100);

    public BFG10KProjectile(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, BFG10K_DAMAGE, Q3DamageSources.BFG10K_DAMAGE, 75);
    }

    public BFG10KProjectile(World world) { this(Entities.BFG10K_PROJECTILE, world); }

    @Environment(EnvType.CLIENT)
    private void playSound() {
        TrackedSound flyingSound = new TrackedSound(this, Sounds.PLASMABALL_FLYING);
        MinecraftClient.getInstance().getSoundManager().play(flyingSound);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        if (getWorld().isClient) playSound();

        super.onSpawnPacket(packet);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        if (!getWorld().isClient) doDamage(entity);
    }

    @Override
    protected void onCollision2(HitResult hitResult) {
        if (!getWorld().isClient) {
            getWorld().playSound(null, getBlockPos(), Sounds.PLASMABALL_HIT, SoundCategory.NEUTRAL);
            despawn();
        }
    }

    @Override
    protected ParticleEffect getParticleType() {
        return ParticleTypes.ELECTRIC_SPARK;
    }
}
