package com.bytemaniak.mcquake3.network.events;

import com.bytemaniak.mcquake3.data.QuakeMapsParameters;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.util.MiscUtils;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class QuakeMatchState implements ServerTickEvents.StartWorldTick {
    private final int FRAG_LIMIT = 30;

    private class PlayerStat {
        int frags = 0;
        int deaths = 0;
        boolean ready = false;
    }

    private enum MatchState {
        WARMUP_STATE, READY_STATE, IN_PROGRESS_STATE, ENDMATCH_STATE
    }

    public QuakeMapsParameters.MapData map = null;
    private MatchState matchState = MatchState.WARMUP_STATE;
    private final Map<String, PlayerStat> stats = new HashMap<>();
    private int highestFrags = 0;
    private int ticksLeft;

    private List<ServerPlayerEntity> getQuakePlayers(ServerWorld world) {
        return world.getPlayers(player -> ((QuakePlayer)player).playingQuakeMap());
    }

    public void recordDeath(ServerPlayerEntity player, Entity attacker) {
        if (attacker instanceof ServerPlayerEntity attackerPlayer && attackerPlayer != player) {
            int frags = ++stats.get(attackerPlayer.getName().getString()).frags;
            if (frags > highestFrags) highestFrags = frags;
        }

        stats.get(player.getName().getString()).deaths++;
    }

    public void spawnQuakePlayer(ServerPlayerEntity player, QuakeMapsParameters.MapData map) {
        player.fallDistance = 0;
        if (!player.isInTeleportationState()) {
            QuakeMapsParameters.MapData.Spawnpoint spawnpoint =
                    map.spawnpoints.get(ThreadLocalRandom.current().nextInt(map.spawnpoints.size()));
            player.networkHandler.requestTeleport(spawnpoint.position.x, spawnpoint.position.y, spawnpoint.position.z, spawnpoint.yaw, 0);

            player.getInventory().clear();
            player.giveItemStack(new ItemStack(Weapons.GAUNTLET));
            player.giveItemStack(new ItemStack(Weapons.MACHINEGUN));
            MiscUtils.insertInNonHotbarInventory(new ItemStack(Weapons.BULLET, Weapons.MACHINEGUN.startingAmmo), player.getInventory());

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(Weapons.MACHINEGUN.slot);
            ServerPlayNetworking.send(player, Packets.SCROLL_TO_SLOT, buf);
        }

        player.setHealth(player.getMaxHealth());
    }

    private void updatePlayerList(ServerWorld world) {
        List<ServerPlayerEntity> players = getQuakePlayers(world).stream().toList();
        List<String> playerNames = players.stream().map(plr -> plr.getName().getString()).toList();

        // First establish if there are any new players
        for (String name : playerNames) {
            if (stats.get(name) == null) {
                for (ServerPlayerEntity player : players)
                    player.sendMessage(Text.of(name+" has joined the arena"));

                stats.put(name, new PlayerStat());
            }
        }

        // Then remove players who are not in the match
        stats.entrySet().removeIf(plr -> {
            boolean remove = playerNames.stream().noneMatch(name -> name.equals(plr.getKey()));
            if (remove)
                for (ServerPlayerEntity player : players)
                    player.sendMessage(Text.of(plr.getKey()+" has left the arena"));
            return remove;
        });
    }

    @Override
    public void onStartTick(ServerWorld world) {
        if (world.getDimensionKey() != Blocks.Q3_DIMENSION_TYPE) return;

        updatePlayerList(world.getServer().getWorld(Blocks.Q3_DIMENSION));

        if (map == null) {
            QuakeMapsParameters state = QuakeMapsParameters.getServerState(world.getServer());
            map = state.getRandomMap();
            if (map == null) return;
        }

        switch (matchState) {
            case WARMUP_STATE -> {
                boolean allReady = true;
                // TODO: Implement ready button
                /*for (PlayerStat stat : stats.values())
                    if (!stat.ready) {
                        allReady = false;
                        break;
                    }*/

                if (allReady) {
                    ticksLeft = MiscUtils.toTicks(3)+1;
                    matchState = MatchState.READY_STATE;
                }
            }
            case READY_STATE -> {
                if (ticksLeft % MiscUtils.toTicks(1) == 0) {
                    switch (ticksLeft / MiscUtils.toTicks(1)) {
                        // TODO: Play countdown sounds to players
                        case 3 -> {break;}
                        case 2 -> {break;}
                        case 1 -> {break;}
                        case 0 -> {
                            ticksLeft = MiscUtils.toTicks(1200);

                            // Allow 20 minutes per match
                            matchState = MatchState.IN_PROGRESS_STATE;

                            for (ServerPlayerEntity player : getQuakePlayers(world))
                                spawnQuakePlayer(player, map);
                        }
                    }
                }
            }
            case IN_PROGRESS_STATE -> {
                if (highestFrags >= FRAG_LIMIT || ticksLeft == 0) {
                    ticksLeft = MiscUtils.toTicks(15);
                    matchState = MatchState.ENDMATCH_STATE;
                }
            }
            case ENDMATCH_STATE -> {
                // TODO: Go to next map after the endmatch period ends
            }
        }

        if (ticksLeft > 0)
            ticksLeft--;
    }
}
