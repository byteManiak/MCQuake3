package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.entity.JumppadEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;

public class JumppadPowerS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        Entity entity = MinecraftClient.getInstance().world.getEntityById(buf.readInt());
        if (entity instanceof JumppadEntity) ((JumppadEntity) entity).updatePower(buf.readByte());
    }
}
