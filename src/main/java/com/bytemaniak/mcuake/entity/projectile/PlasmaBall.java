package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Entities;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class PlasmaBall extends SimpleProjectile {
    private static final int PLASMAGUN_QUAKE_DAMAGE = 20;
    private static final int PLASMAGUN_MC_DAMAGE = 4;

    public PlasmaBall(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, PLASMAGUN_QUAKE_DAMAGE, PLASMAGUN_MC_DAMAGE, DamageSources.PLASMAGUN_DAMAGE, 75);
    }

    public PlasmaBall(World world) { this(Entities.PLASMA_BALL, world); }
}
