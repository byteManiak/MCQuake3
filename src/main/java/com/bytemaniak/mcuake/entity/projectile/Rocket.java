package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Entities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Rocket extends SimpleProjectile {
    private static final int ROCKET_QUAKE_DAMAGE = 100;
    private static final int ROCKET_MC_DAMAGE = 4;

    public Rocket(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, ROCKET_QUAKE_DAMAGE, ROCKET_MC_DAMAGE, DamageSources.ROCKET_DAMAGE, 75);
    }

    public Rocket(World world) { this(Entities.ROCKET, world); }

    @Override
    public void onCollision(HitResult hitResult)
    {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            Vec3d pos = this.getPos();
            DamageSource damageSource = new DamageSources.QuakeDamageSource(this.damageType, this.getOwner());
            this.world.createExplosion(this, damageSource, null, pos.x, pos.y, pos.z, 3, false, World.ExplosionSourceType.NONE);
            this.kill();
        }
    }

    @Override
    protected void despawn() {
        Vec3d pos = this.getPos();
        DamageSource damageSource = new DamageSources.QuakeDamageSource(this.damageType, this.getOwner());
        this.world.createExplosion(this, damageSource, null, pos.x, pos.y, pos.z, 3, false, World.ExplosionSourceType.NONE);

        super.despawn();
    }
}
