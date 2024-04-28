package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.registry.Statistics;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class AddMedalC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        byte value = buf.readByte();
        player.incrementStat(switch (value) {
            case 1 -> Statistics.EXCELLENT_MEDALS;
            case 2 -> Statistics.IMPRESSIVE_MEDALS;
            case 3 -> Statistics.GAUNTLET_MEDALS;
            default -> new Identifier("");
        });
    }
}
