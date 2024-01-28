package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.registry.Q3StatusEffects;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.sound.SoundUtils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class DealtDamageS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (client.player.hasStatusEffect(Q3StatusEffects.QUAD_DAMAGE)) SoundUtils.playSoundLocally(Sounds.DAMAGE_DEALT, 1, .65f);
        else SoundUtils.playSoundLocally(Sounds.DAMAGE_DEALT);
    }
}
