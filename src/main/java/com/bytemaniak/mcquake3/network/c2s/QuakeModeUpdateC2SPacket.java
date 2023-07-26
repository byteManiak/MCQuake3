package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class QuakeModeUpdateC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (player instanceof QuakePlayer quakePlayer) {
            quakePlayer.toggleQuakeMode();
        }
    }
}
