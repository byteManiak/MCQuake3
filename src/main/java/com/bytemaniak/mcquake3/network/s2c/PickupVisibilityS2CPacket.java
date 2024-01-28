package com.bytemaniak.mcquake3.network.s2c;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class PickupVisibilityS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        BlockPos blockPos = buf.readBlockPos();
        boolean shouldRender = buf.readBoolean();

        if (client.world.getBlockEntity(blockPos) instanceof PickupEntity entity)
            entity.lastShouldRender = shouldRender;
    }
}
