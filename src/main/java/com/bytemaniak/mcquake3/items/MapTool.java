package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.MCQuake3;
import com.bytemaniak.mcquake3.data.QuakeMapState;
import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MapTool extends Item {
    private final int CREATE_INITIAL_MODE = 0;
    private final int ADD_SPAWNPOINT_MODE = 1;

    private final String[] modeNames = {"Create/modify map", "Add spawnpoint"};

    public MapTool() {
        super(new Settings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient || world.getDimensionKey() != Blocks.Q3_DIMENSION_TYPE) return TypedActionResult.pass(stack);

        NbtCompound nbt = stack.getOrCreateNbt();
        int mode = nbt.getInt("tool_mode");
        if (user.isSneaking()) {
            mode = 1-mode;
            nbt.putInt("tool_mode", mode);
            user.sendMessage(Text.of("Tool mode: "+modeNames[mode]), true);
            return TypedActionResult.success(stack);
        }

        QuakeMapState state = QuakeMapState.getServerState(((ServerWorld) world).getServer());
        String mapName = "test_map"; // TODO: Get map name via a screen handler instead
        switch (mode) {
            case CREATE_INITIAL_MODE:
                if (state.getMap(mapName) == null)
                    state.createInitialMapData(mapName);
                user.sendMessage(Text.of("Started creation of test map \""+mapName+'"'), true);
                break;
            case ADD_SPAWNPOINT_MODE:
                Vec3d spawnpoint = user.getBlockPos().toCenterPos();
                state.addSpawnpoint(mapName, spawnpoint);
                user.sendMessage(Text.of("Added spawnpoint "+spawnpoint+"to map \""+mapName+'"'), true);
                break;
        }

        for (QuakeMapState.MapData data : state.maps) {
            MCQuake3.LOGGER.info(data.mapName+":");
            for (Vec3d spawnpoint : data.spawnpoints)
                MCQuake3.LOGGER.info("\t"+spawnpoint);
        }
        state.markDirty();
        return super.use(world, user, hand);
    }
}
