package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Packets;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record PickupVisibilityS2CPacket(BlockPos blockPos, boolean shouldRender) implements CustomPayload {
    public static final CustomPayload.Id<PickupVisibilityS2CPacket> ID = new CustomPayload.Id<>(Packets.AMMO_BOX_UPDATE);
    public static final PacketCodec<ByteBuf, PickupVisibilityS2CPacket> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, PickupVisibilityS2CPacket::blockPos,
            PacketCodecs.BOOL, PickupVisibilityS2CPacket::shouldRender,
            PickupVisibilityS2CPacket::new
    );

    public static void receive(PickupVisibilityS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            BlockPos blockPos = payload.blockPos;
            boolean shouldRender = payload.shouldRender;

            if (context.client().world.getBlockEntity(blockPos) instanceof PickupEntity entity)
                entity.lastShouldRender = shouldRender;
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
