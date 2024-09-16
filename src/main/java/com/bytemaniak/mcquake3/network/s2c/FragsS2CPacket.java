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

public record FragsS2CPacket(int frags) implements CustomPayload {
    public static final CustomPayload.Id<FragsS2CPacket> ID = new CustomPayload.Id<>(Packets.FRAGS);
    public static final PacketCodec<ByteBuf, FragsS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, FragsS2CPacket::frags,
            FragsS2CPacket::new
    );

    @Environment(EnvType.CLIENT)
    public static void receive(FragsS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            Renderers.hudRenderer.frags = payload.frags;
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
