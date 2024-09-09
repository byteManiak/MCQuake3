package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Statistics;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.scoreboard.ScoreAccess;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.Consumer;

public record AddMedalC2SPacket(byte value) implements CustomPayload {
    public static final CustomPayload.Id<AddMedalC2SPacket> ID = new Id<>(Packets.ADD_MEDAL);
    public static final PacketCodec<ByteBuf, AddMedalC2SPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BYTE, AddMedalC2SPacket::value,
            AddMedalC2SPacket::new
    );

    public static void receive(AddMedalC2SPacket payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            ServerPlayerEntity player = context.player();
            byte value = payload.value;
            Consumer<ScoreAccess> incrementScore = ScoreAccess::incrementScore;

            switch (value) {
                case 1 -> {
                    player.incrementStat(Statistics.EXCELLENT_MEDALS);
                    player.getScoreboard().forEachScore(Statistics.EXCELLENT_CRITERIA, player, incrementScore);
                }
                case 2 -> {
                    player.incrementStat(Statistics.IMPRESSIVE_MEDALS);
                    player.getScoreboard().forEachScore(Statistics.IMPRESSIVE_CRITERIA, player, incrementScore);
                }
                case 3 -> {
                    player.incrementStat(Statistics.GAUNTLET_MEDALS);
                    player.getScoreboard().forEachScore(Statistics.GAUNTLET_CRITERIA, player, incrementScore);
                }
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
