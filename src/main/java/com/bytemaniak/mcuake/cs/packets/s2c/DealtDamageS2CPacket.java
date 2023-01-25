package com.bytemaniak.mcuake.cs.packets.s2c;

import com.bytemaniak.mcuake.registry.Sounds;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.network.PacketByteBuf;

public class DealtDamageS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(Sounds.DAMAGE_DEALT, 1.f, 1.f));
    }
}
