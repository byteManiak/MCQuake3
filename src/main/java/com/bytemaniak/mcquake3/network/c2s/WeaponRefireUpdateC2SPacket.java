package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import com.bytemaniak.mcquake3.registry.Packets;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record WeaponRefireUpdateC2SPacket() implements CustomPayload {
    public static final CustomPayload.Id<WeaponRefireUpdateC2SPacket> ID = new Id<>(Packets.WEAPON_REFIRE_UPDATE);
    public static final PacketCodec<ByteBuf, WeaponRefireUpdateC2SPacket> CODEC = PacketCodec.unit(new WeaponRefireUpdateC2SPacket());

    public static void receive(WeaponRefireUpdateC2SPacket payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            QuakePlayer qPlayer = (QuakePlayer) context.player();
            qPlayer.mcquake3$setQLRefireRate(!qPlayer.mcquake3$hasQLRefireRate());
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
