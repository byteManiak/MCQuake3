package com.bytemaniak.mcquake3.network.events;

import com.bytemaniak.mcquake3.data.QuakeArenasParameters;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Sounds;
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

    public QuakeArenasParameters.ArenaData arena = null;
    private MatchState matchState = MatchState.WARMUP_STATE;
    private final Map<String, PlayerStat> stats = new HashMap<>();
    private int highestFrags = 0;
    private int ticksLeft;
    private List<ServerPlayerEntity> quakePlayers;

    private List<ServerPlayerEntity> getQuakePlayers(ServerWorld world) {
        return world.getPlayers(player -> ((QuakePlayer)player).inQuakeArena());
    }

    public void recordDeath(ServerPlayerEntity player, Entity attacker) {
        if (attacker instanceof ServerPlayerEntity attackerPlayer && attackerPlayer != player) {
            int frags = ++stats.get(attackerPlayer.getName().getString()).frags;
            if (frags > highestFrags) highestFrags = frags;
        }

        stats.get(player.getName().getString()).deaths++;
    }

    public void spawnQuakePlayer(ServerPlayerEntity player, QuakeArenasParameters.ArenaData arena) {
        player.fallDistance = 0;
        if (!player.isInTeleportationState()) {
            QuakeArenasParameters.ArenaData.Spawnpoint spawnpoint =
                    arena.spawnpoints.get(ThreadLocalRandom.current().nextInt(arena.spawnpoints.size()));
            player.networkHandler.requestTeleport(spawnpoint.position.x, spawnpoint.position.y, spawnpoint.position.z, spawnpoint.yaw, 0);

            player.getInventory().clear();
            player.getInventory().insertStack(Weapons.GAUNTLET.slot, new ItemStack(Weapons.GAUNTLET));
            player.getInventory().insertStack(Weapons.MACHINEGUN.slot, new ItemStack(Weapons.MACHINEGUN));
            MiscUtils.insertInNonHotbarInventory(new ItemStack(Weapons.BULLET, Weapons.MACHINEGUN.startingAmmo), player.getInventory());

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeByte(Weapons.MACHINEGUN.slot);
            ServerPlayNetworking.send(player, Packets.SCROLL_TO_SLOT, buf);
        }

        player.setHealth(player.getMaxHealth());
    }

    private void updatePlayerList(ServerWorld world) {
        quakePlayers = getQuakePlayers(world).stream().toList();
        List<String> playerNames = quakePlayers.stream().map(plr -> plr.getName().getString()).toList();

        // First establish if there are any new players
        for (String name : playerNames) {
            if (stats.get(name) == null) {
                for (ServerPlayerEntity player : quakePlayers)
                    player.sendMessage(Text.of(name+" has joined the arena"));

                stats.put(name, new PlayerStat());
            }
        }

        // Then remove players who are not in the match
        stats.entrySet().removeIf(plr -> {
            boolean remove = playerNames.stream().noneMatch(name -> name.equals(plr.getKey()));
            if (remove)
                for (ServerPlayerEntity player : quakePlayers)
                    player.sendMessage(Text.of(plr.getKey()+" has left the arena"));
            return remove;
        });

        if (quakePlayers.isEmpty()) {
            highestFrags = 0;
            matchState = MatchState.WARMUP_STATE;
        }
    }

    @Override
    public void onStartTick(ServerWorld world) {
        if (world.getDimensionKey() != Blocks.Q3_DIMENSION_TYPE) return;

        updatePlayerList(world.getServer().getWorld(Blocks.Q3_DIMENSION));

        if (arena == null) {
            QuakeArenasParameters state = QuakeArenasParameters.getServerState(world.getServer());
            QuakeArenasParameters.ArenaData newArena = state.getRandomArena(null);
            if (newArena == null || newArena.spawnpoints.isEmpty()) return;
            arena = newArena;

            matchState = MatchState.WARMUP_STATE;
        }

        // No point getting out of warmup state if there aren't at least 2 players
        if (quakePlayers.size() < 2) {
            for (ServerPlayerEntity player : quakePlayers)
                player.sendMessage(Text.of("Waiting for more players..."), true);

            matchState = MatchState.WARMUP_STATE;
            return;
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
                    ticksLeft = MiscUtils.toTicks(11);
                    matchState = MatchState.READY_STATE;
                }
            }
            case READY_STATE -> {
                if (ticksLeft % MiscUtils.toTicks(1) == 0) {
                    switch (ticksLeft / MiscUtils.toTicks(1)) {
                        case 10 -> {
                            PacketByteBuf buf = PacketByteBufs.create();
                            buf.writeIdentifier(Sounds.PREPARE.getId());
                            for (ServerPlayerEntity player : quakePlayers) {
                                player.sendMessage(Text.of("Prepare to fight"), true);
                                ServerPlayNetworking.send(player, Packets.PLAY_ANNOUNCER_SOUND, buf);
                            }
                        }
                        case 3 -> {
                            PacketByteBuf buf = PacketByteBufs.create();
                            buf.writeIdentifier(Sounds.THREE.getId());
                            for (ServerPlayerEntity player : quakePlayers) {
                                player.sendMessage(Text.of(3+""), true);
                                ServerPlayNetworking.send(player, Packets.PLAY_ANNOUNCER_SOUND, buf);
                            }
                        }
                        case 2 -> {
                            PacketByteBuf buf = PacketByteBufs.create();
                            buf.writeIdentifier(Sounds.TWO.getId());
                            for (ServerPlayerEntity player : quakePlayers) {
                                player.sendMessage(Text.of(2+""), true);
                                ServerPlayNetworking.send(player, Packets.PLAY_ANNOUNCER_SOUND, buf);
                            }
                        }
                        case 1 -> {
                            PacketByteBuf buf = PacketByteBufs.create();
                            buf.writeIdentifier(Sounds.ONE.getId());
                            for (ServerPlayerEntity player : quakePlayers) {
                                player.sendMessage(Text.of(1+""), true);
                                ServerPlayNetworking.send(player, Packets.PLAY_ANNOUNCER_SOUND, buf);
                            }
                        }
                        case 0 -> {
                            PacketByteBuf buf = PacketByteBufs.create();
                            buf.writeIdentifier(Sounds.FIGHT.getId());
                            for (ServerPlayerEntity player : quakePlayers) {
                                player.sendMessage(Text.of("Fight"), true);
                                ServerPlayNetworking.send(player, Packets.PLAY_ANNOUNCER_SOUND, buf);
                            }

                            // Allow 20 minutes per match
                            ticksLeft = MiscUtils.toTicks(1200);

                            for (ServerPlayerEntity player : getQuakePlayers(world))
                                spawnQuakePlayer(player, arena);

                            matchState = MatchState.IN_PROGRESS_STATE;
                        }
                    }
                }
            }
            case IN_PROGRESS_STATE -> {
                if (highestFrags >= FRAG_LIMIT || ticksLeft == 0) {
                    // TODO: Announce the winner

                    // Allow 15 seconds before transition to next map
                    ticksLeft = MiscUtils.toTicks(15);
                    matchState = MatchState.ENDMATCH_STATE;
                }
            }
            case ENDMATCH_STATE -> {
                if (ticksLeft == 0) {
                    QuakeArenasParameters state = QuakeArenasParameters.getServerState(world.getServer());
                    arena = state.getRandomArena(arena.arenaName);

                    for (ServerPlayerEntity player : getQuakePlayers(world))
                        spawnQuakePlayer(player, arena);

                    matchState = MatchState.WARMUP_STATE;
                }
            }
        }

        if (ticksLeft > 0)
            ticksLeft--;
    }
}
