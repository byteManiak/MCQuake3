package com.bytemaniak.mcquake3.data;

import com.bytemaniak.mcquake3.MCQuake3;
import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Objects;

public class QuakeMapState extends PersistentState {
    public static class MapData {
        public static class Spawnpoint {
            public Vec3d position;
            public float yaw;

            public Spawnpoint(Vec3d position, float yaw) {
                this.position = position;
                this.yaw = yaw;
            }
        }

        public String mapName;
        public List<Spawnpoint> spawnpoints = Lists.newArrayList();
    }

    public final List<MapData> maps = Lists.newArrayList();
    public int activeMap = 0;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList mapsNbt = new NbtList();

        for (MapData map : maps) {
            NbtCompound mapNbt = new NbtCompound();
            NbtList spawnpoints = new NbtList();

            mapNbt.putString("map_name", map.mapName);

            for (MapData.Spawnpoint i : map.spawnpoints) {
                NbtCompound spawnpoint = new NbtCompound();
                spawnpoint.putDouble("x", i.position.x);
                spawnpoint.putDouble("y", i.position.y);
                spawnpoint.putDouble("z", i.position.z);
                spawnpoint.putFloat("yaw", i.yaw);
                spawnpoints.add(spawnpoint);
            }
            mapNbt.put("spawnpoints", spawnpoints);

            mapsNbt.add(mapNbt);
        }

        nbt.put("q3maps_data", mapsNbt);
        nbt.putInt("active_map", activeMap);
        return nbt;
    }

    private static QuakeMapState readNbt(NbtCompound nbt) {
        QuakeMapState state = new QuakeMapState();

        NbtList mapsData = nbt.getList("q3maps_data", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < mapsData.size(); i++) {
            MapData mapData = new MapData();
            NbtCompound mapDataNbt = mapsData.getCompound(i);

            mapData.mapName = mapDataNbt.getString("map_name");

            NbtList spawnpoints = mapDataNbt.getList("spawnpoints", NbtElement.COMPOUND_TYPE);
            for (int j = 0; j < spawnpoints.size(); j++) {
                NbtCompound spawnpointNbt = spawnpoints.getCompound(j);
                Vec3d position = new Vec3d(spawnpointNbt.getDouble("x"), spawnpointNbt.getDouble("y"), spawnpointNbt.getDouble("z"));
                float yaw = spawnpointNbt.getFloat("yaw");

                mapData.spawnpoints.add(new MapData.Spawnpoint(position, yaw));
            }

            state.maps.add(mapData);
        }

        state.activeMap = nbt.getInt("active_map");

        return state;
    }

    public static QuakeMapState getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(Blocks.Q3_DIMENSION).getPersistentStateManager();
        return persistentStateManager.getOrCreate(QuakeMapState::readNbt, QuakeMapState::new, "mcquake3");
    }

    public void createInitialMapData(String mapName) {
        MapData mapData = new MapData();
        mapData.mapName = mapName;

        maps.add(mapData);
        logUpdates();
    }

    public MapData getMap(String mapName) {
        return maps.stream().filter(e -> Objects.equals(e.mapName, mapName)).findFirst().orElse(null);
    }

    public MapData getActiveMap() {
        return maps.get(activeMap);
    }

    public void addSpawnpoint(String mapName, Vec3d spawnpoint, float yaw) {
        MapData map = getMap(mapName);
        map.spawnpoints.add(new MapData.Spawnpoint(spawnpoint, yaw));
        logUpdates();
    }

    public void deleteMap(String mapName) {
        maps.removeIf(map -> map.mapName.equals(mapName));
        logUpdates();
    }

    private void logUpdates() {
        for (QuakeMapState.MapData data : maps) {
            MCQuake3.LOGGER.info(data.mapName+":");
            for (MapData.Spawnpoint spawnpoint : data.spawnpoints)
                MCQuake3.LOGGER.info("\t"+spawnpoint.position+" "+spawnpoint.yaw);
        }
    }
}
