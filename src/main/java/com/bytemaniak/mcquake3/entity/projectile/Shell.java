package com.bytemaniak.mcquake3.entity.projectile;

import com.bytemaniak.mcquake3.registry.Entities;
import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import com.bytemaniak.mcquake3.util.MiscUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class Shell extends SimpleProjectile {
    private static final float SHOTGUN_DAMAGE = MiscUtils.toMCDamage(10);

    public Shell(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, SHOTGUN_DAMAGE, Q3DamageSources.SHOTGUN_DAMAGE, Q3DamageSources.SHOTGUN_DAMAGE_SELF, 3);
    }

    public Shell(World world) { this(Entities.SHELL, world); }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!getWorld().isClient) {
            Entity entity = entityHitResult.getEntity();
            doDamage(entity);
        }
    }
}
