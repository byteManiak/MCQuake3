package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.gui.FeedbackManager;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Q3StatusEffects;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.client.Renderers;
import com.bytemaniak.mcquake3.sound.SoundUtils;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;

public record DealtDamageS2CPacket(boolean arg) implements CustomPayload {
    public static final CustomPayload.Id<DealtDamageS2CPacket> ID = new CustomPayload.Id<>(Packets.DEALT_DAMAGE);
    public static final PacketCodec<ByteBuf, DealtDamageS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, DealtDamageS2CPacket::arg,
            DealtDamageS2CPacket::new
    );

    @Environment(EnvType.CLIENT)
    public static void receive(DealtDamageS2CPacket payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            if (context.player().hasStatusEffect(Registries.STATUS_EFFECT.getEntry(Q3StatusEffects.QUAD_DAMAGE)))
                SoundUtils.playSoundLocally(Sounds.DAMAGE_DEALT, 1, .65f);
            else SoundUtils.playSoundLocally(Sounds.DAMAGE_DEALT);

            Renderers.feedbacks.pushEvent(FeedbackManager.Event.WEAPON_HIT, payload.arg);
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
