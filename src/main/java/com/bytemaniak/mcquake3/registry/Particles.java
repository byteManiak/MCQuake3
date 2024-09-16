package com.bytemaniak.mcquake3.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Particles {
    public static final SimpleParticleType PLASMA_SPARK = FabricParticleTypes.simple();
    public static final SimpleParticleType ROCKET_TRAIL = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of("mcquake3:plasma_spark"), PLASMA_SPARK);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of("mcquake3:rocket_trail"), ROCKET_TRAIL);
    }
}
