package com.bytemaniak.mcquake3.particle;

import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class PlasmaSparkParticleFactory extends GlowParticle.ElectricSparkFactory {
    public PlasmaSparkParticleFactory(SpriteProvider spriteProvider) {
        super(spriteProvider);
    }

    @Override
    public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        return super.createParticle(defaultParticleType, clientWorld, x, y-0.5, z, velocityX, velocityY, velocityZ);
    }
}
