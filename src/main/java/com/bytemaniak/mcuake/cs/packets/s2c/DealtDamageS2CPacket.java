package com.bytemaniak.mcuake.cs.packets.s2c;

import com.bytemaniak.mcuake.registry.Sounds;
import com.bytemaniak.mcuake.utils.SoundUtils;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class DealtDamageS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        SoundUtils.playSoundLocally(Sounds.DAMAGE_DEALT);
    }
}
