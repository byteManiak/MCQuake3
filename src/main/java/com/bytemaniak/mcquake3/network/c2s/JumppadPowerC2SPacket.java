package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.entity.JumppadEntity;
import com.bytemaniak.mcquake3.screen.JumppadScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class JumppadPowerC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (player.currentScreenHandler instanceof JumppadScreenHandler) {
            // Update the jump pad stats with the ones received from the GUI user
            // and broadcast back to all players
            JumppadEntity entity = ((JumppadScreenHandler)player.currentScreenHandler).entity;
            PacketByteBuf retBuf = PacketByteBufs.create();
            entity.updatePower(buf.readByte());
            retBuf.writeInt(entity.getId());
            retBuf.writeByte(entity.getPower());
            ///for (ServerPlayerEntity plr : PlayerLookup.world(player.getServerWorld()))
                ///ServerPlayNetworking.send(plr, Packets.JUMPPAD_UPDATED_POWER, retBuf);
        }
    }
}
