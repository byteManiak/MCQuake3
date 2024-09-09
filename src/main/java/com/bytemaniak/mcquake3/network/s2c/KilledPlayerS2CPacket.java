package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.gui.FeedbackManager;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.client.Renderers;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record KilledPlayerS2CPacket(boolean arg) implements CustomPayload {
    public static final CustomPayload.Id<KilledPlayerS2CPacket> ID = new CustomPayload.Id<>(Packets.KILLED_PLAYER);
    public static final PacketCodec<ByteBuf, KilledPlayerS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, KilledPlayerS2CPacket::arg,
            KilledPlayerS2CPacket::new
    );

    public static void receive(KilledPlayerS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> Renderers.feedbacks.pushEvent(FeedbackManager.Event.PLAYER_KILL, payload.arg));
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
