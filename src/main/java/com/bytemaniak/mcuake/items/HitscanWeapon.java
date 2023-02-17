package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.cs.CSMessages;
import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
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

    protected HitscanWeapon(MCuakePlayer.WeaponSlot weaponSlot, long refireRateInTicks, SoundEvent firingSound,
                            int quakeDamageAmount, int mcDamageAmount, float hitscanRange, String damageType) {
        super(weaponSlot, refireRateInTicks, firingSound);
        this.hitscanRange = hitscanRange;
        this.quakeDamageAmount = quakeDamageAmount;
        this.mcDamageAmount = mcDamageAmount;
        this.damageType = damageType;
    }

    protected abstract void onProjectileCollision(World world, Vec3d userPos, Vec3d iterPos);
    protected abstract void onQuakeDamage(World world, PlayerEntity attacked);

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        // Spawn a new bullet in front of the player
        Vec3d eyePos = user.getEyePos();
        Vec3d lookDir = Vec3d.fromPolar(user.getPitch(), user.getYaw());
        Vec3d pos = eyePos;

        for (float i = 0; i < hitscanRange; i += .5f) {
            pos = pos.add(lookDir);
            Vec3d minPos = pos.add(new Vec3d(-.1f, -.1f, -.1f));
            Vec3d maxPos = pos.add(new Vec3d(.1f, .1f, .1f));
            BlockPos blockPos = new BlockPos(pos);

            Box collisionBox = new Box(minPos, maxPos);

            LivingEntity collided = world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, user, eyePos.x, eyePos.y, eyePos.z, collisionBox);
            if (collided != null) {
                DamageSource damageSource = new DamageSources.QuakeDamageSource(damageType, user);
                if (collided instanceof PlayerEntity playerEntity && playerEntity.isAlive()) {
                    MCuakePlayer quakePlayer = (MCuakePlayer) playerEntity;
                    if (quakePlayer.isInQuakeMode()) {
                        quakePlayer.takeDamage(quakeDamageAmount, damageSource);

                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeInt(quakePlayer.getQuakeHealth());
                        buf.writeInt(quakePlayer.getQuakeArmor());
                        ServerPlayNetworking.send((ServerPlayerEntity) user, CSMessages.DEALT_DAMAGE, PacketByteBufs.empty());
                        onQuakeDamage(world, playerEntity);
                    } else {
                        collided.damage(damageSource, mcDamageAmount);
                    }
                } else {
                    collided.damage(damageSource, mcDamageAmount);
                }

                onProjectileCollision(world, user.getPos(), pos);
                break;
            }

            VoxelShape collisionShape = world.getBlockState(blockPos).getCollisionShape(world, blockPos);
            if (collisionShape != VoxelShapes.empty()) {
                Box blockCollisionBox = collisionShape.getBoundingBox().offset(blockPos);
                if (blockCollisionBox.intersects(collisionBox)) {
                    onProjectileCollision(world, user.getPos(), pos);
                    break;
                }
            }
        }

        onProjectileCollision(world, user.getPos(), pos);
    }
}
