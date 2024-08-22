package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.registry.client.Renderers;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class FragsS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        Renderers.hudRenderer.frags = buf.readInt();
        Renderers.hudRenderer.highestFrags = buf.readInt();
    }
}
