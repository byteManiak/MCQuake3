package com.bytemaniak.mcquake3.entity.projectile;

import com.bytemaniak.mcquake3.registry.Entities;
import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.sound.TrackedSound;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Rocket extends SimpleProjectile implements GeoEntity {
    private static final int ROCKET_DAMAGE = 4;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public Rocket(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, ROCKET_DAMAGE, Q3DamageSources.ROCKET_DAMAGE, 75);
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
            DamageSource damageSource = Q3DamageSources.of(world, damageType, this, getOwner());
            this.world.createExplosion(this, damageSource, null, pos.x, pos.y, pos.z, 3, false, World.ExplosionSourceType.NONE);
            this.kill();
        }
    }

    @Override
    protected void despawn() {
        Vec3d pos = this.getPos();
        DamageSource damageSource = Q3DamageSources.of(world, damageType, this, getOwner());
        this.world.createExplosion(this, damageSource, null, pos.x, pos.y, pos.z, 3, false, World.ExplosionSourceType.NONE);

        super.despawn();
    }

    @Override
    protected ParticleEffect getParticleType() {
        return ParticleTypes.POOF;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }
}
