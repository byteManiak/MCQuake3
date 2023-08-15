package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.entity.projectile.*;
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
    public static EntityType<Rocket> ROCKET;
    public static EntityType<BFG10KProjectile> BFG10K_PROJECTILE;

    public static void loadEntities() {
        PLASMA_BALL = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier("mcquake3:plasmaball"),
                FabricEntityTypeBuilder.<PlasmaBall>create(SpawnGroup.MISC, PlasmaBall::new)
                        .dimensions(EntityDimensions.fixed(0.3f, 0.3f))
                        .trackRangeBlocks(128).trackedUpdateRate(10)
                        .build());

        SHELL = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier("mcquake3:shell"),
                FabricEntityTypeBuilder.<Shell>create(SpawnGroup.MISC, Shell::new)
                        .dimensions(EntityDimensions.fixed(0.05f, 0.05f))
                        .trackRangeBlocks(128).trackedUpdateRate(10)
                        .build());

        GRENADE = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier("mcquake3:grenade"),
                FabricEntityTypeBuilder.<Grenade>create(SpawnGroup.MISC, Grenade::new)
                        .dimensions(EntityDimensions.fixed(0.3f, 0.3f))
                        .trackRangeBlocks(128).trackedUpdateRate(10)
                        .build());

        ROCKET = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier("mcquake3:rocket"),
                FabricEntityTypeBuilder.<Rocket>create(SpawnGroup.MISC, Rocket::new)
                        .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                        .trackRangeBlocks(128).trackedUpdateRate(10)
                        .build());

        BFG10K_PROJECTILE = Registry.register(
                Registries.ENTITY_TYPE,
                new Identifier("mcquake3:bfg10k"),
                FabricEntityTypeBuilder.<BFG10KProjectile>create(SpawnGroup.MISC, BFG10KProjectile::new)
                        .dimensions(EntityDimensions.fixed(0.75f, 0.75f))
                        .trackRangeBlocks(128).trackedUpdateRate(10)
                        .build());
    }
}
