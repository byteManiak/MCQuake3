package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.MCuake;
import com.bytemaniak.mcuake.registry.DamageSources;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class PlasmaBall extends SimpleProjectile {
    private static int PLASMAGUN_QUAKE_DAMAGE = 10;
    private static int PLASMAGUN_MC_DAMAGE = 4;

    public PlasmaBall(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, PLASMAGUN_QUAKE_DAMAGE, PLASMAGUN_MC_DAMAGE, DamageSources.PLASMAGUN_DAMAGE, 75);
    }

    public PlasmaBall(World world) { this(MCuake.PLASMA_BALL, world); }
}
