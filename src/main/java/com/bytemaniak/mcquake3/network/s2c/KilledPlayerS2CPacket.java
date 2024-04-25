package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.gui.FeedbackManager;
import com.bytemaniak.mcquake3.registry.client.Renderers;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class KilledPlayerS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        Renderers.feedbacks.pushEvent(FeedbackManager.Event.PLAYER_KILL, false);
    }
}
