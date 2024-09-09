package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.sound.SoundUtils;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public record PlayAnnouncerSoundS2CPacket(Identifier soundId) implements CustomPayload {
    public static final CustomPayload.Id<PlayAnnouncerSoundS2CPacket> ID = new Id<>(Packets.PLAY_ANNOUNCER_SOUND);
    public static final PacketCodec<ByteBuf, PlayAnnouncerSoundS2CPacket> CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC, PlayAnnouncerSoundS2CPacket::soundId,
            PlayAnnouncerSoundS2CPacket::new
    );

    public static void receive(PlayAnnouncerSoundS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            Identifier soundId = payload.soundId;
            SoundUtils.playSoundLocally(SoundEvent.of(soundId));
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
