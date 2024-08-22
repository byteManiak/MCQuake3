package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.screen.ArenaBrowserScreen;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class SendArenaNamesS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (client.currentScreen instanceof ArenaBrowserScreen screen) {
            int size = buf.readInt();
            for (int i = 0; i < size; i++) {
                screen.arenaList.addArenaEntry(buf.readString());
            }
        }
    }
}
