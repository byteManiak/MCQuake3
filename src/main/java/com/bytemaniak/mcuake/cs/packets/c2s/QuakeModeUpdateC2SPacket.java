package com.bytemaniak.mcuake.cs.packets.c2s;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class QuakeModeUpdateC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (player instanceof MCuakePlayer quakePlayer) {
            quakePlayer.toggleQuakeMode();
        }
    }
}
