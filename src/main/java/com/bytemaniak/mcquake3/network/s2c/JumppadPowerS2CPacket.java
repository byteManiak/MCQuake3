package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.entity.JumppadEntity;
import com.bytemaniak.mcquake3.registry.Packets;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record JumppadPowerS2CPacket(int entityId, byte power) implements CustomPayload {
    public static final CustomPayload.Id<JumppadPowerS2CPacket> ID = new CustomPayload.Id<>(Packets.JUMPPAD_UPDATED_POWER);
    public static final PacketCodec<ByteBuf, JumppadPowerS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, JumppadPowerS2CPacket::entityId,
            PacketCodecs.BYTE, JumppadPowerS2CPacket::power,
            JumppadPowerS2CPacket::new);

    public static void receive(JumppadPowerS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            Entity entity = MinecraftClient.getInstance().world.getEntityById(payload.entityId);
            if (entity instanceof JumppadEntity) ((JumppadEntity) entity).updatePower(payload.power);
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
