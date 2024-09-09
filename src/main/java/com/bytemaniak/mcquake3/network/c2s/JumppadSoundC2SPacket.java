package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Sounds;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;

public record JumppadSoundC2SPacket(BlockPos pos) implements CustomPayload {
    public static final CustomPayload.Id<JumppadSoundC2SPacket> ID = new Id<>(Packets.JUMPPAD_SOUND);
    public static final PacketCodec<ByteBuf, JumppadSoundC2SPacket> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, JumppadSoundC2SPacket::pos,
            JumppadSoundC2SPacket::new
    );

    public static void receive(JumppadSoundC2SPacket payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            BlockPos pos = payload.pos;
            ServerPlayerEntity player = context.player();
            player.getWorld().playSound(player, pos, Sounds.JUMPPAD_BOOST, SoundCategory.BLOCKS, 1, 1);
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
