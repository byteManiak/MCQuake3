package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.blocks.JumppadEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class JumppadPowerS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        BlockPos pos = buf.readBlockPos();
        BlockEntity entity = MinecraftClient.getInstance().world.getBlockEntity(pos);
        if (entity instanceof JumppadEntity) ((JumppadEntity) entity).updatePower(buf.readFloat(), buf.readFloat());
    }
}
