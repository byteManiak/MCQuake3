package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Entities;
import com.bytemaniak.mcuake.registry.Sounds;
import com.bytemaniak.mcuake.sound.TrackedSound;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Rocket extends SimpleProjectile {
    private static final int ROCKET_QUAKE_DAMAGE = 100;
    private static final int ROCKET_MC_DAMAGE = 4;

    public Rocket(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, ROCKET_QUAKE_DAMAGE, ROCKET_MC_DAMAGE, DamageSources.ROCKET_DAMAGE, 75);
    }

    public Rocket(World world) { this(Entities.ROCKET, world); }

    @Environment(EnvType.CLIENT)
    private void playSound() {
        TrackedSound flyingSound = new TrackedSound(this, Sounds.ROCKET_FLYING);
        MinecraftClient.getInstance().getSoundManager().play(flyingSound);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        if (world.isClient) playSound();

        super.onSpawnPacket(packet);
    }

    @Override
    public void onCollision(HitResult hitResult)
    {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            Vec3d pos = this.getPos();
            DamageSource damageSource = new DamageSources.QuakeDamageSource(this.damageType, this.getOwner());
            this.world.createExplosion(this, damageSource, null, pos.x, pos.y, pos.z, 3, false, World.ExplosionSourceType.NONE);
            this.kill();
        }
    }

    @Override
    protected void despawn() {
        Vec3d pos = this.getPos();
        DamageSource damageSource = new DamageSources.QuakeDamageSource(this.damageType, this.getOwner());
        this.world.createExplosion(this, damageSource, null, pos.x, pos.y, pos.z, 3, false, World.ExplosionSourceType.NONE);

        super.despawn();
    }

    @Override
    protected ParticleEffect getParticleType() {
        return ParticleTypes.POOF;
    }
}
