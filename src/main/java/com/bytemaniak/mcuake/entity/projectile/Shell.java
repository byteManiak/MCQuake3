package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Entities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class Shell extends SimpleProjectile {
    private static final int SHOTGUN_QUAKE_DAMAGE = 10;
    private static final int SHOTGUN_MC_DAMAGE = 4;

    public Shell(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, SHOTGUN_QUAKE_DAMAGE, SHOTGUN_MC_DAMAGE, DamageSources.SHOTGUN_DAMAGE, 3);
    }

    public Shell(World world) { this(Entities.SHELL, world); }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        if (!this.world.isClient) {
            doDamage(entity);
        }
    }

    @Override
    public void onCollision(HitResult hitResult)
    {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.kill();
        }
    }
}
