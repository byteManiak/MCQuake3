package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record PlayerClassUpdateC2SPacket(String playerClass) implements CustomPayload {
    public static final CustomPayload.Id<PlayerClassUpdateC2SPacket> ID = new Id<>(Packets.PLAYER_CLASS_UPDATE);
    public static final PacketCodec<ByteBuf, PlayerClassUpdateC2SPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, PlayerClassUpdateC2SPacket::playerClass,
            PlayerClassUpdateC2SPacket::new
    );

    public static void receive(PlayerClassUpdateC2SPacket payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> ((QuakePlayer)context.player()).setPlayerVoice(payload.playerClass));
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
