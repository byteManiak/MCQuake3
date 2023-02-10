package com.bytemaniak.mcuake.cs.packets.c2s;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class DealtGauntletDamageC2SPacket {
    private static final int GAUNTLET_QUAKE_DAMAGE = 5;
    private static final int GAUNTLET_MC_DAMAGE = 2;

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        Entity attacked = player.world.getEntityById(buf.readInt());
        if (attacked instanceof MCuakePlayer quakePlayer) {
            quakePlayer.takeDamage(GAUNTLET_QUAKE_DAMAGE, DamageSources.GAUNTLET_DAMAGE);
        } else {
            attacked.damage(DamageSources.GAUNTLET_DAMAGE, GAUNTLET_MC_DAMAGE);
        }
    }
}
