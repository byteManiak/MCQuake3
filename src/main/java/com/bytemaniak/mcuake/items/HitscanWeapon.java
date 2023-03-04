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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public abstract class HitscanWeapon extends Weapon {
    private final float hitscanRange;
    private final int quakeDamageAmount;
    private final int mcDamageAmount;
    private final String damageType;

    protected HitscanWeapon(QuakePlayer.WeaponSlot weaponSlot, long refireRateInTicks,
                            boolean hasRepeatedFiringSound, SoundEvent firingSound, boolean hasActiveLoopSound,
                            int quakeDamageAmount, int mcDamageAmount, float hitscanRange, String damageType) {
        super(weaponSlot, refireRateInTicks, hasRepeatedFiringSound, firingSound, hasActiveLoopSound);
        this.hitscanRange = hitscanRange;
        this.quakeDamageAmount = quakeDamageAmount;
        this.mcDamageAmount = mcDamageAmount;
        this.damageType = damageType;
    }

    protected abstract void onProjectileCollision(World world, Vec3d userPos, Vec3d iterPos);
    protected abstract void onQuakeDamage(World world, LivingEntity attacked);
    protected abstract void onMcDamage(World world, LivingEntity attacked);

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        // Spawn a new bullet in front of the player
        Vec3d eyePos = user.getEyePos();
        Vec3d lookDir = Vec3d.fromPolar(user.getPitch(), user.getYaw());
        Vec3d pos = eyePos;

        for (float i = 0; i < hitscanRange; i += .4f) {
            pos = pos.add(lookDir);
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

                onProjectileCollision(world, eyePos.add(0, -user.getStandingEyeHeight()/2, 0), pos);
                return;
            }

            if (world.isChunkLoaded(blockPos)) {
                VoxelShape collisionShape = world.getBlockState(blockPos).getCollisionShape(world, blockPos);
                if (collisionShape != VoxelShapes.empty()) {
                    Box blockCollisionBox = collisionShape.getBoundingBox().offset(blockPos);
                    if (blockCollisionBox.intersects(collisionBox)) {
                        onProjectileCollision(world, eyePos.add(0, -user.getStandingEyeHeight()/2, 0), pos);
                        return;
                    }
                }
            }
        }

        onProjectileCollision(world, eyePos.add(0, -user.getStandingEyeHeight()/2, 0), pos);
    }
}
