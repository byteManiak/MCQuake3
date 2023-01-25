package com.bytemaniak.mcuake.cs.packets.s2c;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class PlayerStatsS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        MCuakePlayer player = (MCuakePlayer) client.player;
        player.setQuakeHealth(buf.readInt());
        player.setQuakeArmor(buf.readInt());
    }
}
