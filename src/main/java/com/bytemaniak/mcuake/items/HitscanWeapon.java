package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public abstract class HitscanWeapon extends Weapon {
    private final static float HITSCAN_RADIUS = .1f;
    private final static float HITSCAN_VERTICAL_OFFSET = .3f;

    private final int quakeDamageAmount;
    private final int mcDamageAmount;
    private final String damageType;

    private final float hitscanRange;
    private final float hitscanNumSteps;

    protected HitscanWeapon(QuakePlayer.WeaponSlot weaponSlot, Identifier id, long refireRateInTicks,
                            boolean hasRepeatedFiringSound, SoundEvent firingSound, boolean hasActiveLoopSound,
                            int quakeDamageAmount, int mcDamageAmount, String damageType,
                            float hitscanRange, float hitscanStepDistance) {
        super(weaponSlot, id, refireRateInTicks, hasRepeatedFiringSound, firingSound, hasActiveLoopSound);

        this.quakeDamageAmount = quakeDamageAmount;
        this.mcDamageAmount = mcDamageAmount;
        this.damageType = damageType;

        this.hitscanRange = hitscanRange;
        this.hitscanNumSteps = hitscanRange/hitscanStepDistance;
    }

    protected HitscanWeapon(QuakePlayer.WeaponSlot weaponSlot, Identifier id, long refireRateInTicks,
                            boolean hasRepeatedFiringSound, SoundEvent firingSound, boolean hasActiveLoopSound,
                            int quakeDamageAmount, int mcDamageAmount, String damageType,
                            float hitscanRange) {
        this(weaponSlot, id, refireRateInTicks, hasRepeatedFiringSound, firingSound, hasActiveLoopSound,
                quakeDamageAmount, mcDamageAmount, damageType, hitscanRange, .35f);
    }

    protected void onProjectileCollision(World world, LivingEntity user, Vec3d userPos, Vec3d iterPos) {}
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
            BlockPos blockPos = new BlockPos(pos);

            Box collisionBox = new Box(minPos, maxPos);

            LivingEntity collided = world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, user, eyePos.x, eyePos.y, eyePos.z, collisionBox);
            if (collided != null) {
                DamageSource damageSource = new DamageSources.QuakeDamageSource(damageType, user);
                if (collided.isAlive() && collided instanceof QuakePlayer quakePlayer && quakePlayer.isInQuakeMode()) {
                    collided.damage(damageSource, quakeDamageAmount);
                } else {
                    collided.damage(damageSource, mcDamageAmount);
                }

                onDamage(world, collided);
                onProjectileCollision(world, user, offsetWeaponPos, pos);
                return;
            }

            if (world.isChunkLoaded(blockPos)) {
                VoxelShape collisionShape = world.getBlockState(blockPos).getCollisionShape(world, blockPos);
                if (collisionShape != VoxelShapes.empty()) {
                    Box blockCollisionBox = collisionShape.getBoundingBox().offset(blockPos);
                    if (blockCollisionBox.intersects(collisionBox)) {
                        onProjectileCollision(world, user, offsetWeaponPos, pos);
                        return;
                    }
                }
            }
        }

        onProjectileCollision(world, user, offsetWeaponPos, pos);
    }
}
