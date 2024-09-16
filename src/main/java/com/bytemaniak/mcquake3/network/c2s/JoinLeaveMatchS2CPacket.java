package com.bytemaniak.mcquake3.network.c2s;

import com.bytemaniak.mcquake3.data.QuakeArenasParameters;
import com.bytemaniak.mcquake3.network.s2c.FragsS2CPacket;
import com.bytemaniak.mcquake3.network.s2c.ScrollToSlotS2CPacket;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.ServerEvents;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.util.MiscUtils;
import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

public record JoinLeaveMatchS2CPacket(boolean leave) implements CustomPayload {
    public static final CustomPayload.Id<JoinLeaveMatchS2CPacket> ID = new Id<>(Packets.JOIN_LEAVE_MATCH);
    public static final PacketCodec<ByteBuf, JoinLeaveMatchS2CPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, JoinLeaveMatchS2CPacket::leave,
            JoinLeaveMatchS2CPacket::new
    );

    public static void receive(JoinLeaveMatchS2CPacket payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            MinecraftServer server = context.server();
            ServerPlayerEntity player = context.player();
            boolean leave = payload.leave;

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
                    Vec3d position = spawnpoint.position();
                    player.teleport(server.getWorld(Blocks.Q3_DIMENSION), position.x, position.y, position.z, spawnpoint.yaw(), 0);
                    player.getInventory().clear();
                    player.getInventory().insertStack(Weapons.GAUNTLET.slot, new ItemStack(Weapons.GAUNTLET));
                    player.getInventory().insertStack(Weapons.MACHINEGUN.slot, new ItemStack(Weapons.MACHINEGUN));
                    MiscUtils.insertInNonHotbarInventory(new ItemStack(Weapons.BULLET, 100), player.getInventory());

                    ScrollToSlotS2CPacket replyBuf = new ScrollToSlotS2CPacket((byte)Weapons.MACHINEGUN.slot);
                    ServerPlayNetworking.send(player, replyBuf);

                    FragsS2CPacket replyBuf2 = new FragsS2CPacket(0);
                    ServerPlayNetworking.send(player, replyBuf2);
                } else player.sendMessage(Text.of("Arena " + arena.arenaName + " has no spawnpoints"), true);
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}
