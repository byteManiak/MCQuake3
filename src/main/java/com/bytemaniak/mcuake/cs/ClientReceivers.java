package com.bytemaniak.mcuake.cs;

import com.bytemaniak.mcuake.blocks.JumppadEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class ClientReceivers {
    public static void init()
    {
        ClientPlayNetworking.registerGlobalReceiver(CSMessages.JUMPPAD_UPDATED_POWER, (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            BlockEntity entity = MinecraftClient.getInstance().world.getBlockEntity(pos);
            if (entity instanceof JumppadEntity)
            {
                ((JumppadEntity) entity).updatePower(buf.readFloat(), buf.readFloat());
            }
        });
    }
}
