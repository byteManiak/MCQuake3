package com.bytemaniak.mcuake.cs;

import com.bytemaniak.mcuake.blocks.jumppad.JumppadEntity;
import com.bytemaniak.mcuake.blocks.jumppad.JumppadScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class ServerReceivers {
    public static void init()
    {
        ServerPlayNetworking.registerGlobalReceiver(CSMessages.JUMPPAD_UPDATE_POWER, (server, player, handler, buf, responseSender) -> {
            if (player.currentScreenHandler instanceof JumppadScreenHandler)
            {
                // Update the jump pad stats with the ones received from the GUI user
                // and broadcast back to all players
                JumppadEntity entity = ((JumppadScreenHandler)player.currentScreenHandler).entity;
                PacketByteBuf retBuf = PacketByteBufs.create();
                entity.updatePower(buf.readFloat(), buf.readFloat());
                retBuf.writeBlockPos(entity.getPos());
                retBuf.writeFloat(entity.forward_power);
                retBuf.writeFloat(entity.up_power);
                entity.markDirty();
                for (ServerPlayerEntity plr : PlayerLookup.world(player.getWorld()))
                    ServerPlayNetworking.send(plr, CSMessages.JUMPPAD_UPDATED_POWER, retBuf);
            }
        });
    }
}
