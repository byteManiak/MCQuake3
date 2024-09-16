package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.client.Renderers;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record HighestFragsS2CPacket(int highestFrags) implements CustomPayload {
    public static final CustomPayload.Id<HighestFragsS2CPacket> ID = new CustomPayload.Id<>(Packets.HIGHEST_FRAGS);
    public static final PacketCodec<ByteBuf, HighestFragsS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, HighestFragsS2CPacket::highestFrags,
            HighestFragsS2CPacket::new
    );

    @Environment(EnvType.CLIENT)
    public static void receive(HighestFragsS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            Renderers.hudRenderer.frags = payload.highestFrags;
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
