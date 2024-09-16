package com.bytemaniak.mcquake3.data;

import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.ServerEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class QuakeArenasParameters extends PersistentState {
    public static class ArenaData {
        public record Spawnpoint(Vec3d position, float yaw) {}

        public String arenaName;
        public final List<Spawnpoint> spawnpoints = new ArrayList<>();
    }

    public final List<ArenaData> arenas = new ArrayList<>();
    public int activeArena = 0;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList arenasNbt = new NbtList();

        for (ArenaData arena : arenas) {
            NbtCompound arenaNbt = new NbtCompound();
            NbtList spawnpoints = new NbtList();

            arenaNbt.putString("Arena_name", arena.arenaName);

            for (ArenaData.Spawnpoint i : arena.spawnpoints) {
                NbtCompound spawnpoint = new NbtCompound();
                spawnpoint.putDouble("x", i.position.x);
                spawnpoint.putDouble("y", i.position.y);
                spawnpoint.putDouble("z", i.position.z);
                spawnpoint.putFloat("yaw", i.yaw);
                spawnpoints.add(spawnpoint);
            }
            arenaNbt.put("spawnpoints", spawnpoints);

            arenasNbt.add(arenaNbt);
        }

        nbt.put("q3Arenas_data", arenasNbt);
        nbt.putInt("active_Arena", activeArena);
        return nbt;
    }

    private static QuakeArenasParameters readNbt(NbtCompound nbt) {
        QuakeArenasParameters state = new QuakeArenasParameters();

        NbtList arenasData = nbt.getList("q3Arenas_data", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < arenasData.size(); i++) {
            ArenaData arenaData = new ArenaData();
            NbtCompound arenaDataNbt = arenasData.getCompound(i);

            arenaData.arenaName = arenaDataNbt.getString("Arena_name");

            NbtList spawnpoints = arenaDataNbt.getList("spawnpoints", NbtElement.COMPOUND_TYPE);
            for (int j = 0; j < spawnpoints.size(); j++) {
                NbtCompound spawnpointNbt = spawnpoints.getCompound(j);
                Vec3d position = new Vec3d(spawnpointNbt.getDouble("x"), spawnpointNbt.getDouble("y"), spawnpointNbt.getDouble("z"));
                float yaw = spawnpointNbt.getFloat("yaw");

                arenaData.spawnpoints.add(new ArenaData.Spawnpoint(position, yaw));
            }

            state.arenas.add(arenaData);
        }

        state.activeArena = nbt.getInt("active_Arena");

        return state;
    }

    public static QuakeArenasParameters getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(Blocks.Q3_DIMENSION).getPersistentStateManager();
        return persistentStateManager.getOrCreate(QuakeArenasParameters::readNbt, QuakeArenasParameters::new, "mcquake3_Arenas");
    }

    public void createInitialArenaData(String arenaName) {
        if (getArena(arenaName) == null) {
            ArenaData arenaData = new ArenaData();
            arenaData.arenaName = arenaName;

            arenas.add(arenaData);
            markDirty();
        }
    }

    public ArenaData getArena(String arenaName) {
        return arenas.stream().filter(e -> Objects.equals(e.arenaName, arenaName)).findFirst().orElse(null);
    }

    public ArenaData getRandomArena(String except) {
        if (arenas.isEmpty()) return null;
        if (arenas.size() == 1) return arenas.get(0);

        ArenaData nextArena = arenas.get(ThreadLocalRandom.current().nextInt(arenas.size()));
        while (nextArena.arenaName.equals(except))
            nextArena = arenas.get(ThreadLocalRandom.current().nextInt(arenas.size()));

        return nextArena;
    }

    public boolean addSpawnpoint(String arenaName, Vec3d spawnpoint, float yaw) {
        ArenaData arena = getArena(arenaName);
        if (arena == null) return false;

        arena.spawnpoints.add(new ArenaData.Spawnpoint(spawnpoint, yaw));
        markDirty();

        ArenaData matchArena = ServerEvents.QUAKE_MATCH_STATE.arena;
        if (matchArena != null && arenaName.equals(matchArena.arenaName))
            ServerEvents.QUAKE_MATCH_STATE.arena = arena;

        return true;
    }

    public void deleteArena(String arenaName) {
        arenas.removeIf(arena -> arena.arenaName.equals(arenaName));
        markDirty();

        ArenaData matchArena = ServerEvents.QUAKE_MATCH_STATE.arena;
        if (matchArena != null && arenaName.equals(matchArena.arenaName))
            ServerEvents.QUAKE_MATCH_STATE.arena = getRandomArena(null);
    }
}
