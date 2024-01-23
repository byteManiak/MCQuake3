package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import com.bytemaniak.mcquake3.registry.Q3StatusEffects;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public abstract class HitscanWeapon extends Weapon {
    private final static float HITSCAN_RADIUS = .1f;
    private final static float HITSCAN_VERTICAL_OFFSET = .3f;

    private final float damageAmount;
    private final RegistryKey<DamageType> damageType;

    private final float hitscanRange;
    private final float hitscanNumSteps;

    protected HitscanWeapon(QuakePlayer.WeaponSlot weaponSlot, Identifier id, long refireRateInTicks,
                            boolean hasRepeatedFiringSound, SoundEvent firingSound, boolean hasActiveLoopSound,
                            float damageAmount, RegistryKey<DamageType> damageType,
                            float hitscanRange, float hitscanStepDistance) {
        super(weaponSlot, id, refireRateInTicks, hasRepeatedFiringSound, firingSound, hasActiveLoopSound);

        this.damageAmount = damageAmount;
        this.damageType = damageType;

        this.hitscanRange = hitscanRange;
        this.hitscanNumSteps = hitscanRange/hitscanStepDistance;
    }

    protected HitscanWeapon(QuakePlayer.WeaponSlot weaponSlot, Identifier id, long refireRateInTicks,
                            boolean hasRepeatedFiringSound, SoundEvent firingSound, boolean hasActiveLoopSound,
                            float damageAmount, RegistryKey<DamageType> damageType,
                            float hitscanRange) {
        this(weaponSlot, id, refireRateInTicks, hasRepeatedFiringSound, firingSound, hasActiveLoopSound,
                damageAmount, damageType, hitscanRange, .25f);
    }

    protected void onProjectileCollision(World world, LivingEntity user, Vec3d userPos, Vec3d iterPos, boolean isBlockCollision) {}

    protected void onDamage(World world, LivingEntity attacked) {}

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack, Vec3d lookDir, Vec3d weaponPos) {
        Vec3d eyePos = user.getEyePos();

        // The furthest hitscan point, to which the projectile will go towards
        Vec3d destPos = eyePos.add(lookDir.multiply(hitscanRange));

        // The hitscan projectile's initial position is approximated
        // to be shot from the held weapon, not from the player's eye
        Vec3d step = destPos.subtract(weaponPos).multiply(1/hitscanNumSteps);
        Vec3d pos = weaponPos;

        // Optional offset used to draw the weapon's projectile
        Vec3d upDir = Vec3d.fromPolar(user.getPitch() + 90, user.getYaw());
        Vec3d offsetWeaponPos = weaponPos.add(upDir.multiply(HITSCAN_VERTICAL_OFFSET));

        for (int i = 0; i < hitscanNumSteps; i++) {
            pos = pos.add(step);
            Vec3d minPos = pos.add(new Vec3d(-HITSCAN_RADIUS, -HITSCAN_RADIUS, -HITSCAN_RADIUS));
            Vec3d maxPos = pos.add(new Vec3d(HITSCAN_RADIUS, HITSCAN_RADIUS, HITSCAN_RADIUS));

            Vec3i posI = new Vec3i((int)Math.floor(pos.x), (int)Math.floor(pos.y), (int)Math.floor(pos.z));
            BlockPos blockPos = new BlockPos(posI);

            Box collisionBox = new Box(minPos, maxPos);

            LivingEntity collided = world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, user, eyePos.x, eyePos.y, eyePos.z, collisionBox);
            float damage = user.hasStatusEffect(Q3StatusEffects.QUAD_DAMAGE) ? damageAmount*3 : damageAmount;

            if (collided != null) {
                if (!world.isClient) {
                    DamageSource damageSource = Q3DamageSources.of(world, damageType, user, user);
                    collided.damage(damageSource, damage);
                    onDamage(world, collided);
                    if (collided instanceof PlayerEntity)
                        ServerPlayNetworking.send((ServerPlayerEntity) user, Packets.DEALT_DAMAGE, PacketByteBufs.empty());
                }
                onProjectileCollision(world, user, offsetWeaponPos, pos, false);
                return;
            }

            if (world.isChunkLoaded(blockPos)) {
                VoxelShape collisionShape = world.getBlockState(blockPos).getCollisionShape(world, blockPos);
                if (collisionShape != VoxelShapes.empty()) {
                    Box blockCollisionBox = collisionShape.getBoundingBox().offset(blockPos);
                    if (blockCollisionBox.intersects(collisionBox)) {
                        onProjectileCollision(world, user, offsetWeaponPos, pos, true);
                        return;
                    }
                }
            }
        }

        onProjectileCollision(world, user, offsetWeaponPos, pos, false);
    }
}
