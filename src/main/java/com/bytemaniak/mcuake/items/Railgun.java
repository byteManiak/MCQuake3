package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.cs.CSMessages;
import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Sounds;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;

public class Railgun extends Weapon{
    private static final int RAILGUN_REFIRE_TICK_RATE = 50;
    private static final int RAILGUN_QUAKE_DAMAGE = 100;
    private static final int RAILGUN_MC_DAMAGE = 10;

    public Railgun() { super(MCuakePlayer.WeaponSlot.RAILGUN, RAILGUN_REFIRE_TICK_RATE, Sounds.RAILGUN_FIRE); }

    private void sendRailgunTrail(World world, Vec3d startPos, Vec3d endPos) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(startPos.x);
        buf.writeDouble(startPos.y);
        buf.writeDouble(startPos.z);
        buf.writeDouble(endPos.x);
        buf.writeDouble(endPos.y);
        buf.writeDouble(endPos.z);
        for (ServerPlayerEntity plr : PlayerLookup.world((ServerWorld) world))
            ServerPlayNetworking.send(plr, CSMessages.SHOW_RAILGUN_TRAIL, buf);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        // Spawn a new bullet in front of the player
        Vec3d eyePos = user.getEyePos();
        Vec3d lookDir = Vec3d.fromPolar(user.getPitch(), user.getYaw()).multiply(.5f);
        Vec3d pos = eyePos;
        float i;
        for (i = 0; i < 200; i += .5f) {
            pos = pos.add(lookDir);
            Vec3d minPos = pos.add(new Vec3d(-.1f, -.1f, -.1f));
            Vec3d maxPos = pos.add(new Vec3d(.1f, .1f, .1f));
            BlockPos blockPos = new BlockPos(pos);

            Box collisionBox = new Box(minPos, maxPos);

            LivingEntity collided = world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT, user, eyePos.x, eyePos.y, eyePos.z, collisionBox);
            if (collided != null) {
                DamageSource damageSource = new DamageSources.QuakeDamageSource(DamageSources.RAILGUN_DAMAGE, user);
                if (collided instanceof PlayerEntity playerEntity && playerEntity.isAlive()) {
                    MCuakePlayer quakePlayer = (MCuakePlayer) playerEntity;
                    if (quakePlayer.isInQuakeMode()) {
                        quakePlayer.takeDamage(RAILGUN_QUAKE_DAMAGE, damageSource);

                        PacketByteBuf buf = PacketByteBufs.create();
                        buf.writeInt(quakePlayer.getQuakeHealth());
                        buf.writeInt(quakePlayer.getQuakeArmor());
                        ServerPlayNetworking.send((ServerPlayerEntity) user, CSMessages.DEALT_DAMAGE, PacketByteBufs.empty());
                    } else {
                        collided.damage(damageSource, RAILGUN_MC_DAMAGE);
                    }
                } else {
                    collided.damage(damageSource, RAILGUN_MC_DAMAGE);
                }

                sendRailgunTrail(world, user.getPos(), pos);
                break;
            }

            VoxelShape collisionShape = world.getBlockState(blockPos).getCollisionShape(world, blockPos);
            if (collisionShape != VoxelShapes.empty()) {
                Box blockCollisionBox = collisionShape.getBoundingBox().offset(blockPos);
                if (blockCollisionBox.intersects(collisionBox)) {
                    sendRailgunTrail(world, user.getPos(), pos);
                    break;
                }
            }
        }

        sendRailgunTrail(world, user.getPos(), pos);
    }
}
