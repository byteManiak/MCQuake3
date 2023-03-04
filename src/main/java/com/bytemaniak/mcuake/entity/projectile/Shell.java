package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.MCuake;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Entities;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class Shell extends SimpleProjectile {
    private static int SHOTGUN_QUAKE_DAMAGE = 10;
    private static int SHOTGUN_MC_DAMAGE = 4;

    public Shell(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, SHOTGUN_QUAKE_DAMAGE, SHOTGUN_MC_DAMAGE, DamageSources.SHOTGUN_DAMAGE, 3);
    }

    public Shell(World world) { this(Entities.SHELL, world); }
}
