package com.bytemaniak.mcuake.entity.projectile;

import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Entities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class Grenade extends SimpleProjectile {
    private static final int GRENADE_QUAKE_DAMAGE = 20;
    private static final int GRENADE_MC_DAMAGE = 4;

    public Grenade(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, GRENADE_QUAKE_DAMAGE, GRENADE_MC_DAMAGE, DamageSources.GRENADE_DAMAGE, 50);
    }

    public Grenade(World world) { this(Entities.GRENADE, world); }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (!this.world.isClient) {
            Vec3d pos = this.getPos();
            this.world.createExplosion(this, pos.x, pos.y, pos.z, 3, World.ExplosionSourceType.NONE);
            this.kill();
        }
    }

    @Override
    public void onCollision(HitResult hitResult)
    {
        super.onCollision(hitResult);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            Vec3d velocity = this.getVelocity();
            BlockHitResult blockHit = (BlockHitResult) hitResult;
            velocity = switch (blockHit.getSide()) {
                case EAST, WEST -> velocity.multiply(-1, 1, 1);
                case NORTH, SOUTH -> velocity.multiply(1, 1, -1);
                case DOWN -> velocity.multiply(1, -1, 1);
                case UP -> velocity.multiply(1, -.7, 1);
            };
            if (velocity.y > 0 && velocity.y < 0.1f) velocity = velocity.multiply(1, 0, 1);
            this.setVelocity(velocity);
        }
    }

    @Override
    protected void despawn() {
        super.despawn();
        Vec3d pos = this.getPos();
        this.world.createExplosion(this, pos.x, pos.y, pos.z, 3, World.ExplosionSourceType.NONE);
    }

    @Override
    public void tick() {
        super.tick();

        Vec3d velocity = this.getVelocity();
        this.setVelocity(velocity.x, velocity.y - 0.05f, velocity.z);
    }

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float explosionPower) {
        return false;
    }

    @Override
    protected float getDrag() { return .925f; }
}
