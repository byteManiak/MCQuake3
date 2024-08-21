package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.data.QuakeMapsParameters;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.ServerEvents;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.util.MiscUtils;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

public class JoinLeaveMatchS2CPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        if (((QuakePlayer)player).playingQuakeMap()) {
            RegistryKey<World> dimension = player.getSpawnPointDimension();
            ServerWorld world = server.getWorld(dimension);
            BlockPos spawnPos = player.getSpawnPointPosition();
            if (spawnPos == null) spawnPos = world.getSpawnPos();

            player.teleport(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);
        } else {
            QuakeMapsParameters.MapData map = ServerEvents.QUAKE_MATCH_STATE.map;
            if (map == null) {
                player.sendMessage(Text.of("There are no maps on the server"), true);
                return;
            }

            if (!map.spawnpoints.isEmpty()) {
                player.changeGameMode(GameMode.ADVENTURE);

                QuakeMapsParameters.MapData.Spawnpoint spawnpoint = map.spawnpoints.get(ThreadLocalRandom.current().nextInt(map.spawnpoints.size()));
                Vec3d position = spawnpoint.position;
                player.teleport(server.getWorld(Blocks.Q3_DIMENSION), position.x, position.y, position.z, spawnpoint.yaw, 0);
                player.getInventory().clear();
                player.giveItemStack(new ItemStack(Weapons.GAUNTLET));
                player.giveItemStack(new ItemStack(Weapons.MACHINEGUN));
                MiscUtils.insertInNonHotbarInventory(new ItemStack(Weapons.BULLET, 100), player.getInventory());
            } else player.sendMessage(Text.of("Map " + map.mapName + " has no spawnpoints"), true);
        }
    }
}
