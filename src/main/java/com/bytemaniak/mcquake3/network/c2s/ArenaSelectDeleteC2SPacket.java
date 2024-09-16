package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.data.QuakeArenasParameters;
import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ArenaSelectDeleteC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        String arenaName = buf.readString();
        boolean delete = buf.readBoolean();
        QuakeArenasParameters state = QuakeArenasParameters.getServerState(server);

        if (delete) {
            state.deleteArena(arenaName);
            player.sendMessage(Text.of("Deleted arena "+arenaName), true);
        } else {
            boolean startEditing = buf.readBoolean();
            state.createInitialArenaData(arenaName);
            if (startEditing) {
                ((QuakePlayer) player).mcquake3$setCurrentlyEditingArena(arenaName);
                player.sendMessage(Text.of("Selected arena " + arenaName + " to add spawnpoints to"), true);
            }
        }
    }
}
