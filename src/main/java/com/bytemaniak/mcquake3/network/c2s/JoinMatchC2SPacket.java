package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.data.QuakeMapState;
import com.bytemaniak.mcquake3.registry.Blocks;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.concurrent.ThreadLocalRandom;

public class JoinMatchC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        QuakeMapState state = QuakeMapState.getServerState(server);
        QuakeMapState.MapData activeMap = state.getActiveMap();
        if (!activeMap.spawnpoints.isEmpty()) {
            Vec3d spawnpoint = activeMap.spawnpoints.get(ThreadLocalRandom.current().nextInt(activeMap.spawnpoints.size()));
            player.teleport(server.getWorld(Blocks.Q3_DIMENSION), spawnpoint.x, spawnpoint.y, spawnpoint.z, 0, 0);
        } else player.sendMessage(Text.of("Map has no spawnpoints"), true);
    }
}
