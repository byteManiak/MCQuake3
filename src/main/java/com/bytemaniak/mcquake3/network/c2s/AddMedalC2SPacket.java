package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.registry.Statistics;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.Consumer;

public class AddMedalC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        byte value = buf.readByte();
        String name = player.getEntityName();
        Consumer<ScoreboardPlayerScore> incrementScore = ScoreboardPlayerScore::incrementScore;

        switch (value) {
            case 1 -> {
                player.incrementStat(Statistics.EXCELLENT_MEDALS);
                player.getScoreboard().forEachScore(Statistics.EXCELLENT_CRITERIA, name, incrementScore);
            }
            case 2 -> {
                player.incrementStat(Statistics.IMPRESSIVE_MEDALS);
                player.getScoreboard().forEachScore(Statistics.IMPRESSIVE_CRITERIA, name, incrementScore);
            }
            case 3 -> {
                player.incrementStat(Statistics.GAUNTLET_MEDALS);
                player.getScoreboard().forEachScore(Statistics.GAUNTLET_CRITERIA, name, incrementScore);
            }
        }
    }
}
