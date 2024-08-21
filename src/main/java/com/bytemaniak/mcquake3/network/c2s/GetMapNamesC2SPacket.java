package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.data.QuakeMapsParameters;
import com.bytemaniak.mcquake3.registry.Packets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class GetMapNamesC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        QuakeMapsParameters mapsData = QuakeMapsParameters.getServerState(server);
        int size = mapsData.maps.size();

        PacketByteBuf replyBuf = PacketByteBufs.create();
        replyBuf.writeInt(size);
        for (QuakeMapsParameters.MapData mapData : mapsData.maps) {
            replyBuf.writeString(mapData.mapName);
        }
        ServerPlayNetworking.send(player, Packets.SEND_MAP_NAMES, replyBuf);
    }
}
