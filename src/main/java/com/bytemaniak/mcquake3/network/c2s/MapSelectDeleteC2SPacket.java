package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.data.QuakeMapsParameters;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class MapSelectDeleteC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        String mapName = buf.readString();
        boolean delete = buf.readBoolean();
        QuakeMapsParameters state = QuakeMapsParameters.getServerState(server);

        if (delete) {
            state.deleteMap(mapName);
            player.sendMessage(Text.of("Deleted map "+mapName), true);
        } else {
            boolean startEditing = buf.readBoolean();
            state.createInitialMapData(mapName);
            if (startEditing) {
                ((QuakePlayer) player).setCurrentlyEditingMap(mapName);
                player.sendMessage(Text.of("Selected map " + mapName), true);
            }
        }
    }
}
