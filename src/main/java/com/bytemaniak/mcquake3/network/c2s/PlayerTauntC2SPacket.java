package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerTauntC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        QuakePlayer qPlayer = (QuakePlayer) player;
        qPlayer.mcquake3$taunt();
    }
}
