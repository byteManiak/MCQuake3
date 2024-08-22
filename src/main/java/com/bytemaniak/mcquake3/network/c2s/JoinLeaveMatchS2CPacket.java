package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.data.QuakeArenasParameters;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.ServerEvents;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.util.MiscUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
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
        boolean leave = buf.readBoolean();

        if (leave) {
            RegistryKey<World> dimension = player.getSpawnPointDimension();
            ServerWorld world = server.getWorld(dimension);
            BlockPos spawnPos = player.getSpawnPointPosition();
            if (spawnPos == null) spawnPos = world.getSpawnPos();

            player.teleport(world, spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);
        } else {
            QuakeArenasParameters.ArenaData arena = ServerEvents.QUAKE_MATCH_STATE.arena;
            if (arena == null) {
                player.sendMessage(Text.of("There are no arenas on the server"), true);
                return;
            }

            if (!arena.spawnpoints.isEmpty()) {
                player.changeGameMode(GameMode.ADVENTURE);

                QuakeArenasParameters.ArenaData.Spawnpoint spawnpoint = arena.spawnpoints.get(ThreadLocalRandom.current().nextInt(arena.spawnpoints.size()));
                Vec3d position = spawnpoint.position;
                player.teleport(server.getWorld(Blocks.Q3_DIMENSION), position.x, position.y, position.z, spawnpoint.yaw, 0);
                player.getInventory().clear();
                player.getInventory().insertStack(Weapons.GAUNTLET.slot, new ItemStack(Weapons.GAUNTLET));
                player.getInventory().insertStack(Weapons.MACHINEGUN.slot, new ItemStack(Weapons.MACHINEGUN));
                MiscUtils.insertInNonHotbarInventory(new ItemStack(Weapons.BULLET, 100), player.getInventory());

                PacketByteBuf replyBuf = PacketByteBufs.create();
                replyBuf.writeByte(Weapons.MACHINEGUN.slot);
                ServerPlayNetworking.send(player, Packets.SCROLL_TO_SLOT, replyBuf);

                replyBuf = PacketByteBufs.create();
                replyBuf.writeInt(0);
                ServerPlayNetworking.send(player, Packets.FRAGS, replyBuf);
            } else player.sendMessage(Text.of("Arena " + arena.arenaName + " has no spawnpoints"), true);
        }
    }
}
