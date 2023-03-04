package com.bytemaniak.mcuake.network.c2s;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerClassUpdateC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (player instanceof QuakePlayer quakePlayer && quakePlayer.isInQuakeMode()) {
            quakePlayer.setPlayerVoice(buf.readString());
        }
    }
}
