package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.MCuake;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class Bullet extends SimpleProjectile {
    private static int MACHINEGUN_DAMAGE = 3;

    public Bullet(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, MACHINEGUN_DAMAGE, "machinegun");
    }

    public Bullet(World world) { this(MCuake.BULLET, world); }
}
