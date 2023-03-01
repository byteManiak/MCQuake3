package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.cs.CSMessages;
import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Sounds;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Railgun extends HitscanWeapon {
    private static final int RAILGUN_REFIRE_TICK_RATE = 50;
    private static final int RAILGUN_QUAKE_DAMAGE = 100;
    private static final int RAILGUN_MC_DAMAGE = 10;
    private static final float RAILGUN_RANGE = 200;

    public Railgun() {
        super(MCuakePlayer.WeaponSlot.RAILGUN, RAILGUN_REFIRE_TICK_RATE, true, Sounds.RAILGUN_FIRE, false,
                RAILGUN_QUAKE_DAMAGE, RAILGUN_MC_DAMAGE, RAILGUN_RANGE, DamageSources.RAILGUN_DAMAGE);
    }

    @Override
    protected void onQuakeDamage(World world, LivingEntity attacked) {}

    @Override
    protected void onMcDamage(World world, LivingEntity attacked) {}

    @Override
    protected void onProjectileCollision(World world, Vec3d userPos, Vec3d iterPos) {
        sendRailgunTrail(world, userPos, iterPos);
    }

    private void sendRailgunTrail(World world, Vec3d startPos, Vec3d endPos) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(startPos.x);
        buf.writeDouble(startPos.y);
        buf.writeDouble(startPos.z);
        buf.writeDouble(endPos.x);
        buf.writeDouble(endPos.y);
        buf.writeDouble(endPos.z);
        buf.writeInt(MCuakePlayer.WeaponSlot.RAILGUN.slot());
        for (ServerPlayerEntity plr : PlayerLookup.world((ServerWorld) world))
            ServerPlayNetworking.send(plr, CSMessages.SHOW_TRAIL, buf);
    }
}
