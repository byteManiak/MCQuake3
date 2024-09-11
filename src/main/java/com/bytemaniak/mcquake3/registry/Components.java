package com.bytemaniak.mcquake3.registry;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Components {
    public static final ComponentType<Double> FIRING_SPEED = Registry.register(
            Registries.DATA_COMPONENT_TYPE, Identifier.of("mcquake3:firing_speed"),
            ComponentType.<Double>builder().codec(Codec.DOUBLE).build()
    );

    public static void registerComponents() {}
}
