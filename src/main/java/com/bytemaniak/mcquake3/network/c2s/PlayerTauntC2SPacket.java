package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record PlayerTauntC2SPacket() implements CustomPayload {
    public static final CustomPayload.Id<PlayerTauntC2SPacket> ID = new Id<>(Packets.PLAYER_TAUNT);
    public static final PacketCodec<ByteBuf, PlayerTauntC2SPacket> CODEC = PacketCodec.unit(new PlayerTauntC2SPacket());

    public static void receive(PlayerTauntC2SPacket payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            QuakePlayer qPlayer = (QuakePlayer)context.player();
            qPlayer.taunt();
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
