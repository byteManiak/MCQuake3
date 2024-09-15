package com.bytemaniak.mcquake3.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Particles {
    public static final DefaultParticleType PLASMA_SPARK = FabricParticleTypes.simple();
    public static final DefaultParticleType ROCKET_TRAIL = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier("mcquake3:plasma_spark"), PLASMA_SPARK);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier("mcquake3:rocket_trail"), ROCKET_TRAIL);
    }
}
