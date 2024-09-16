package com.bytemaniak.mcquake3.particle;

import net.minecraft.client.particle.ExplosionSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class RocketTrailParticleFactory extends ExplosionSmokeParticle.Factory {
    public RocketTrailParticleFactory(SpriteProvider spriteProvider) {
        super(spriteProvider);
    }

    @Override
    public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        return super.createParticle(defaultParticleType, clientWorld, x, y-0.5, z, velocityX, velocityY, velocityZ);
    }
}
