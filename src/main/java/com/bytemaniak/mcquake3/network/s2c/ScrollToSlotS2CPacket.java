package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.registry.Packets;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record ScrollToSlotS2CPacket(byte slot) implements CustomPayload {
    public static final CustomPayload.Id<ScrollToSlotS2CPacket> ID = new CustomPayload.Id<>(Packets.SCROLL_TO_SLOT);
    public static final PacketCodec<ByteBuf, ScrollToSlotS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BYTE, ScrollToSlotS2CPacket::slot,
            ScrollToSlotS2CPacket::new
    );

    public static void receive(ScrollToSlotS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> context.client().player.getInventory().selectedSlot = payload.slot);
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
