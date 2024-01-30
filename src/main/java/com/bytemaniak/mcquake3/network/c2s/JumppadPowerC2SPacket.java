package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.blocks.JumppadEntity;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.screen.JumppadScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
            entity.updatePower(buf.readFloat(), buf.readFloat());
            retBuf.writeBlockPos(entity.getPos());
            retBuf.writeFloat(entity.forward_power);
            retBuf.writeFloat(entity.up_power);
            entity.markDirty();
            for (ServerPlayerEntity plr : PlayerLookup.world(player.getServerWorld()))
                ServerPlayNetworking.send(plr, Packets.JUMPPAD_UPDATED_POWER, retBuf);
        }
    }
}
