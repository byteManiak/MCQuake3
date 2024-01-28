package com.bytemaniak.mcquake3.entity.projectile;

import com.bytemaniak.mcquake3.registry.Entities;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
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
        super(entityType, world, GRENADE_DAMAGE, Q3DamageSources.GRENADE_DAMAGE, 50);
    }

    public Grenade(World world) { this(Entities.GRENADE, world); }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (!world.isClient) despawn();
    }

    @Override
    public void onCollision(HitResult hitResult)
    {
        super.onCollision(hitResult);

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
            else world.playSound(null, getBlockPos(), Sounds.GRENADE_BOUNCE, SoundCategory.PLAYERS, 1, 1);

            setVelocity(velocity);
        }
    }

    @Override
    protected void despawn() {
        Vec3d pos = getPos();
        DamageSource damageSource = Q3DamageSources.of(world, damageType, this, getOwner());
        Explosion explosion = world.createExplosion(this, damageSource, null,
                pos.x, pos.y, pos.z, 2.875f, false, World.ExplosionSourceType.NONE);
        if (getOwner() != null && !explosion.getAffectedPlayers().isEmpty())
            ServerPlayNetworking.send((ServerPlayerEntity) getOwner(), Packets.DEALT_DAMAGE, PacketByteBufs.empty());

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
