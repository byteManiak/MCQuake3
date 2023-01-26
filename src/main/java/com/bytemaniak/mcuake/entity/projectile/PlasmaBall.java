package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.MCuake;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class PlasmaBall extends SimpleProjectile {
    private static int PLASMAGUN_DAMAGE = 10;

    public PlasmaBall(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, PLASMAGUN_DAMAGE, "machinegun");
    }

    public PlasmaBall(World world) { this(MCuake.PLASMA_BALL, world); }
}
