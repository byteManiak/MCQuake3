package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.registry.Sounds;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class JumppadSoundC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        BlockPos pos = buf.readBlockPos();
        player.getWorld().playSound(player, pos, Sounds.JUMPPAD_BOOST, SoundCategory.BLOCKS, 1, 1);
    }
}
