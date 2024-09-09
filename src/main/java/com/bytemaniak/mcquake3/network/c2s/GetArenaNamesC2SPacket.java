package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.data.QuakeArenasParameters;
import com.bytemaniak.mcquake3.network.s2c.SendArenaNamesS2CPacket;
import com.bytemaniak.mcquake3.registry.Packets;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.ArrayList;
import java.util.List;

public record GetArenaNamesC2SPacket() implements CustomPayload {
    public static final CustomPayload.Id<GetArenaNamesC2SPacket> ID = new Id<>(Packets.GET_ARENA_NAMES);
    public static final PacketCodec<ByteBuf, GetArenaNamesC2SPacket> CODEC = PacketCodec.unit(new GetArenaNamesC2SPacket());

    public static void receive(GetArenaNamesC2SPacket payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            QuakeArenasParameters arenasData = QuakeArenasParameters.getServerState(context.server());

            List<String> mapNames = new ArrayList<>();
            for (QuakeArenasParameters.ArenaData arenaData : arenasData.arenas)
                mapNames.add(arenaData.arenaName);
            SendArenaNamesS2CPacket replyBuf = new SendArenaNamesS2CPacket(mapNames);
            ServerPlayNetworking.send(context.player(), replyBuf);
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
