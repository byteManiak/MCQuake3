package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.sound.SoundUtils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class PlayAnnouncerSoundS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        Identifier soundId = buf.readIdentifier();
        SoundUtils.playSoundLocally(SoundEvent.of(soundId));
    }
}
