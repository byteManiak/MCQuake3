package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.screen.ArenaBrowserScreen;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

import java.util.List;

public record SendArenaNamesS2CPacket(List<String> list) implements CustomPayload {
    public static final CustomPayload.Id<SendArenaNamesS2CPacket> ID = new Id<>(Packets.SEND_ARENA_NAMES);
    public static final PacketCodec<ByteBuf, SendArenaNamesS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING.collect(PacketCodecs.toList()), SendArenaNamesS2CPacket::list,
            SendArenaNamesS2CPacket::new
    );

    public static void receive(SendArenaNamesS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            if (context.client().currentScreen instanceof ArenaBrowserScreen screen)
                for (String mapName : payload.list)
                    screen.arenaList.addArenaEntry(mapName);
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
