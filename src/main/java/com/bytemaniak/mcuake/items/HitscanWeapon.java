package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Packets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public abstract class HitscanWeapon extends Weapon {
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

    protected void onProjectileCollision(World world, Vec3d userPos, Vec3d iterPos) {}
    protected void onQuakeDamage(World world, LivingEntity attacked) {}
    protected void onMcDamage(World world, LivingEntity attacked) {}

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
        Vec3d offsetWeaponPos = weaponPos.add(upDir.multiply(.3));

        for (int i = 0; i < hitscanNumSteps; i++) {
            pos = pos.add(step);
            Vec3d minPos = pos.add(new Vec3d(-.1f, -.1f, -.1f));
            Vec3d maxPos = pos.add(new Vec3d(.1f, .1f, .1f));
            BlockPos blockPos = new BlockPos(pos);

            Box collisionBox = new Box(minPos, maxPos);

            LivingEntity collided = world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, user, eyePos.x, eyePos.y, eyePos.z, collisionBox);
            if (collided != null) {
                DamageSource damageSource = new DamageSources.QuakeDamageSource(damageType, user);
                if (collided instanceof PlayerEntity playerEntity && playerEntity.isAlive()) {
                    QuakePlayer quakePlayer = (QuakePlayer) playerEntity;
                    if (quakePlayer.isInQuakeMode()) {
                        quakePlayer.takeDamage(quakeDamageAmount, damageSource);

                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeInt(quakePlayer.getQuakeHealth());
                        buf.writeInt(quakePlayer.getQuakeArmor());
                        ServerPlayNetworking.send((ServerPlayerEntity) user, Packets.DEALT_DAMAGE, PacketByteBufs.empty());
                        onQuakeDamage(world, playerEntity);
                    } else {
                        onMcDamage(world, collided);
                        collided.damage(damageSource, mcDamageAmount);
                    }
                } else {
                    collided.damage(damageSource, mcDamageAmount);
                    onMcDamage(world, collided);
                }

                onProjectileCollision(world, offsetWeaponPos, pos);
                return;
            }

            if (world.isChunkLoaded(blockPos)) {
                VoxelShape collisionShape = world.getBlockState(blockPos).getCollisionShape(world, blockPos);
                if (collisionShape != VoxelShapes.empty()) {
                    Box blockCollisionBox = collisionShape.getBoundingBox().offset(blockPos);
                    if (blockCollisionBox.intersects(collisionBox)) {
                        onProjectileCollision(world, offsetWeaponPos, pos);
                        return;
                    }
                }
            }
        }

        onProjectileCollision(world, offsetWeaponPos, pos);
    }
}
