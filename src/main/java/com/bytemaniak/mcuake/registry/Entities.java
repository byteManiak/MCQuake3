package com.bytemaniak.mcuake.registry;

import com.bytemaniak.mcuake.entity.projectile.Grenade;
import com.bytemaniak.mcuake.entity.projectile.PlasmaBall;
import com.bytemaniak.mcuake.entity.projectile.Shell;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Entities {
    public static EntityType<PlasmaBall> PLASMA_BALL;
    public static EntityType<Shell> SHELL;
    public static EntityType<Grenade> GRENADE;

    public static void loadEntities() {
        PLASMA_BALL = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier("mcuake", "plasmaball"),
                FabricEntityTypeBuilder.<PlasmaBall>create(SpawnGroup.MISC, PlasmaBall::new)
                        .dimensions(EntityDimensions.fixed(0.3f, 0.3f))
                        .trackRangeBlocks(128).trackedUpdateRate(10)
                        .build());

        SHELL = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier("mcuake", "shell"),
                FabricEntityTypeBuilder.<Shell>create(SpawnGroup.MISC, Shell::new)
                        .dimensions(EntityDimensions.fixed(0.05f, 0.05f))
                        .trackRangeBlocks(128).trackedUpdateRate(10)
                        .build());

        GRENADE = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier("mcuake", "grenade"),
                FabricEntityTypeBuilder.<Grenade>create(SpawnGroup.MISC, Grenade::new)
                        .dimensions(EntityDimensions.fixed(0.3f, 0.3f))
                        .trackedUpdateRate(128).trackedUpdateRate(10)
                        .build());
    }
}
