package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.data.QuakeArenasParameters;
import com.bytemaniak.mcquake3.registry.Packets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class GetArenaNamesC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        QuakeArenasParameters arenasData = QuakeArenasParameters.getServerState(server);
        int size = arenasData.arenas.size();

        PacketByteBuf replyBuf = PacketByteBufs.create();
        replyBuf.writeInt(size);
        for (QuakeArenasParameters.ArenaData arenaData : arenasData.arenas) {
            replyBuf.writeString(arenaData.arenaName);
        }
        ServerPlayNetworking.send(player, Packets.SEND_ARENA_NAMES, replyBuf);
    }
}
