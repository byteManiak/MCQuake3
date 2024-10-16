package com.bytemaniak.mcquake3.entity.projectile;

import com.bytemaniak.mcquake3.entity.JumppadEntity;
import com.bytemaniak.mcquake3.entity.PortalEntity;
import com.bytemaniak.mcquake3.entity.PropEntity;
import com.bytemaniak.mcquake3.registry.Entities;
import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Grenade extends SimpleProjectile implements GeoEntity {
    private static final int GRENADE_DAMAGE = 4;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public Grenade(EntityType<? extends SimpleProjectile> entityType, World world) {
        super(entityType, world, GRENADE_DAMAGE, Q3DamageSources.GRENADE_DAMAGE, Q3DamageSources.GRENADE_DAMAGE_SELF, 50);
    }

    public Grenade(World world) { this(Entities.GRENADE, world); }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (!getWorld().isClient && !(entityHitResult.getEntity() instanceof PropEntity))
            despawn();
    }

    @Override
    protected void onCollision2(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            Vec3d velocity = getVelocity();
            BlockHitResult blockHit = (BlockHitResult) hitResult;
            if (blockHit.isInsideBlock())
                velocity = velocity.multiply(-1, -1, -1);
            else velocity = switch (blockHit.getSide()) {
                case EAST, WEST -> velocity.multiply(-1, 1, 1);
                case NORTH, SOUTH -> velocity.multiply(1, 1, -1);
                case DOWN -> velocity.multiply(1, -1, 1);
                case UP -> velocity.multiply(1, -.55, 1);
            };

            if (velocity.y > 0 && velocity.y < 0.075f) velocity = velocity.multiply(1, 0, 1);
            else getWorld().playSound(null, getBlockPos(), Sounds.GRENADE_BOUNCE, SoundCategory.PLAYERS, 1, 1);

            setVelocity(velocity);
        } else if (hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) hitResult;
            if (entityHit.getEntity() instanceof PropEntity entity) {
                Vec3d velocity = getVelocity();
                if (entity instanceof JumppadEntity jumppadEntity && jumppadEntity.getPower() > 0)
                    velocity = velocity.add(jumppadEntity.getVelocityVector());
                else if (!(entity instanceof PortalEntity))
                    velocity = velocity.multiply(-1, -1, -1);

                if (velocity.y > 0 && velocity.y < 0.075f) velocity = velocity.multiply(1, 0, 1);
                else getWorld().playSound(null, getBlockPos(), Sounds.GRENADE_BOUNCE, SoundCategory.PLAYERS, 1, 1);

                setVelocity(velocity);
            }
        }
    }

    @Override
    protected void despawn() {
        // TODO: Don't create a vanilla explosion
        Vec3d pos = getPos();
        DamageSource damageSource = Q3DamageSources.of(getWorld(), damageType, this, getOwner());
        getWorld().createExplosion(this, damageSource, null,
                pos.x, pos.y, pos.z, 2.875f, false, World.ExplosionSourceType.NONE);

        super.despawn();
    }

    @Override
    public void tick() {
        super.tick();

        Vec3d velocity = getVelocity();
        setVelocity(velocity.x, velocity.y - 0.05f, velocity.z);
    }

    @Override
    public boolean canExplosionDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float explosionPower) {
        return false;
    }

    @Override
    protected float getDrag() { return .925f; }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", state -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }
}
