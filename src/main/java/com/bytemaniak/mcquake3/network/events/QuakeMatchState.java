package com.bytemaniak.mcquake3.network.events;

import com.bytemaniak.mcquake3.data.QuakeArenasParameters;
import com.bytemaniak.mcquake3.network.s2c.FragsS2CPacket;
import com.bytemaniak.mcquake3.network.s2c.HighestFragsS2CPacket;
import com.bytemaniak.mcquake3.network.s2c.PlayAnnouncerSoundS2CPacket;
import com.bytemaniak.mcquake3.network.s2c.ScrollToSlotS2CPacket;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Packets;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.util.MiscUtils;
import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class QuakeMatchState implements ServerTickEvents.StartWorldTick {
    public static final int FRAG_LIMIT = 30;

    private static class PlayerStat {
        int frags = 0;
        int deaths = 0;
    }

    private enum MatchState {
        WARMUP_STATE, READY_STATE, IN_PROGRESS_STATE, ENDMATCH_STATE
    }

    public QuakeArenasParameters.ArenaData arena = null;
    private MatchState matchState = MatchState.WARMUP_STATE;
    private final Map<String, PlayerStat> stats = new HashMap<>();

    // TODO: Also add stat for second best player
    private int highestFrags = 0;
    private String winner;
    private int ticksLeft;
    private List<ServerPlayerEntity> quakePlayers;

    private List<ServerPlayerEntity> getQuakePlayers(ServerWorld world) {
        return world.getPlayers(player -> ((QuakePlayer)player).mcquake3$inQuakeArena());
    }

    public void recordDeath(ServerPlayerEntity player, DamageSource damageSource) {
        if (matchState == MatchState.IN_PROGRESS_STATE) {
            if (damageSource.getAttacker() instanceof ServerPlayerEntity attackerPlayer && attackerPlayer != player) {
                int frags = ++stats.get(attackerPlayer.getName().getString()).frags;
                if (frags > highestFrags) {
                    int fragsLeft = FRAG_LIMIT - frags;
                    switch (fragsLeft) {
                        case 3 -> sendGlobalSound(Sounds.THREE_FRAGS);
                        case 2 -> sendGlobalSound(Sounds.TWO_FRAGS);
                        case 1 -> sendGlobalSound(Sounds.ONE_FRAG);
                        case 0 -> winner = attackerPlayer.getName().getString();
                    }
                    highestFrags = frags;
                }

                FragsS2CPacket buf = new FragsS2CPacket(frags);
                ServerPlayNetworking.send(attackerPlayer, buf);

                HighestFragsS2CPacket buf2 = new HighestFragsS2CPacket(highestFrags);
                sendGlobalPacket(buf2);
            }

            stats.get(player.getName().getString()).deaths++;
        }

        sendGlobalMessage(damageSource.getDeathMessage(player), false);
    }

    public void spawnQuakePlayer(ServerPlayerEntity player, QuakeArenasParameters.ArenaData arena) {
        player.fallDistance = 0;
        if (!player.isInTeleportationState()) {
            QuakeArenasParameters.ArenaData.Spawnpoint spawnpoint =
                    arena.spawnpoints.get(ThreadLocalRandom.current().nextInt(arena.spawnpoints.size()));
            player.networkHandler.requestTeleport(spawnpoint.position().x, spawnpoint.position().y, spawnpoint.position().z, spawnpoint.yaw(), 0);

            player.getInventory().clear();
            player.getInventory().insertStack(Weapons.GAUNTLET.slot, new ItemStack(Weapons.GAUNTLET));
            player.getInventory().insertStack(Weapons.MACHINEGUN.slot, new ItemStack(Weapons.MACHINEGUN));
            MiscUtils.insertInNonHotbarInventory(new ItemStack(Weapons.BULLET, Weapons.MACHINEGUN.startingAmmo), player.getInventory());

            ScrollToSlotS2CPacket buf = new ScrollToSlotS2CPacket((byte)Weapons.MACHINEGUN.slot);
            ServerPlayNetworking.send(player, buf);
        }

        player.setHealth(player.getMaxHealth());
    }

    private void updatePlayerList(ServerWorld world) {
        quakePlayers = getQuakePlayers(world).stream().toList();
        List<String> playerNames = quakePlayers.stream().map(plr -> plr.getName().getString()).toList();

        // First establish if there are any new players
        for (String name : playerNames) {
            if (stats.get(name) == null) {
                sendGlobalMessage(Text.of(name+" has joined the arena"), false);

                stats.put(name, new PlayerStat());
            }
        }

        // Then remove players who are not in the match
        stats.entrySet().removeIf(plr -> {
            boolean remove = playerNames.stream().noneMatch(name -> name.equals(plr.getKey()));
            if (remove)
                sendGlobalMessage(Text.of(plr.getKey()+" has left the arena"), false);
            return remove;
        });

        if (quakePlayers.isEmpty()) {
            highestFrags = 0;
            matchState = MatchState.WARMUP_STATE;
        }
    }

    private void sendSound(ServerPlayerEntity player, SoundEvent sound) {
        PlayAnnouncerSoundS2CPacket buf = new PlayAnnouncerSoundS2CPacket(sound.getId());
        ServerPlayNetworking.send(player, buf);
    }

    private void sendGlobalSound(SoundEvent sound) {
        for (ServerPlayerEntity player : quakePlayers)
            sendSound(player, sound);
    }

    private void sendGlobalMessage(Text message, boolean overlay) {
        for (ServerPlayerEntity player : quakePlayers)
            player.sendMessage(message, overlay);
    }

    private void sendGlobalPacket(CustomPayload buf) {
        for (ServerPlayerEntity player : quakePlayers)
            ServerPlayNetworking.send(player, buf);
    }

    private void sendGlobalMessageWithSound(String message, SoundEvent sound) {
        sendGlobalMessage(Text.of(message), true);
        sendGlobalSound(sound);
    }

    @Override
    public void onStartTick(ServerWorld world) {
        if (!world.getDimensionEntry().matchesKey(Blocks.Q3_DIMENSION_TYPE)) return;

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
            sendGlobalMessage(Text.of("Waiting for more players..."), true);

            matchState = MatchState.WARMUP_STATE;
            return;
        }

        switch (matchState) {
            case WARMUP_STATE -> {
                // TODO: Implement player readiness

                if (highestFrags > 0) {
                    // Reset player stats for match start
                    stats.replaceAll((k, v) -> new PlayerStat());
                    highestFrags = 0;
                    winner = "";

                    sendGlobalPacket(new FragsS2CPacket(0));
                    sendGlobalPacket(new HighestFragsS2CPacket(0));
                }

                ticksLeft = MiscUtils.toTicks(11);
                matchState = MatchState.READY_STATE;
            }
            case READY_STATE -> {
                if (ticksLeft % MiscUtils.toTicks(1) == 0) {
                    switch (ticksLeft / MiscUtils.toTicks(1)) {
                        case 10 -> sendGlobalMessageWithSound("Prepare to fight", Sounds.PREPARE);
                        case 3 -> sendGlobalMessageWithSound("3", Sounds.THREE);
                        case 2 -> sendGlobalMessageWithSound("2", Sounds.TWO);
                        case 1 -> sendGlobalMessageWithSound("1", Sounds.ONE);
                        case 0 -> {
                            sendGlobalMessageWithSound("Fight", Sounds.FIGHT);

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
                    for (ServerPlayerEntity player : getQuakePlayers(world)) {
                        if (player.getName().getString().equals(winner))
                            sendSound(player, Sounds.MATCH_WIN);
                        else sendSound(player, Sounds.MATCH_LOSS);
                    }

                    // Allow 10 seconds before transition to next map
                    ticksLeft = MiscUtils.toTicks(10);
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
