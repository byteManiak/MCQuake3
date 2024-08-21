package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.data.QuakeMapState;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.screen.MapNameScreenHandler;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class MapTool extends Item implements NamedScreenHandlerFactory {
    private final int CREATE_INITIAL_MODE = 0;
    private final int ADD_SPAWNPOINT_MODE = 1;

    private final String[] modeNames = {"Create/modify map", "Add spawnpoint"};

    public MapTool() {
        super(new Settings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient) return TypedActionResult.pass(stack);
        if (world.getDimensionKey() != Blocks.Q3_DIMENSION_TYPE) {
            user.sendMessage(Text.of("You need to be in the Quake3 dimension to use the map tool."), true);
            return TypedActionResult.pass(stack);
        }

        QuakePlayer player = (QuakePlayer)user;
        String mapName = player.getMapToolName();
        int mode = player.getMapToolMode();

        if (user.isSneaking()) {
            if (mode == CREATE_INITIAL_MODE && mapName.isEmpty()) {
                user.sendMessage(Text.of("No map chosen yet"), true);
                return TypedActionResult.pass(stack);
            }
            mode = 1-mode;
            player.setMapToolMode(mode);
            user.sendMessage(Text.of("Tool mode: "+modeNames[mode]), true);
            return TypedActionResult.success(stack);
        }

        QuakeMapState state = QuakeMapState.getServerState(((ServerWorld) world).getServer());
        switch (mode) {
            case CREATE_INITIAL_MODE:
                user.openHandledScreen(this);
                break;
            case ADD_SPAWNPOINT_MODE:
                if (mapName.isEmpty()) {
                    user.sendMessage(Text.of("No map chosen yet"), true);
                    return TypedActionResult.pass(stack);
                }
                if (state.getMap(mapName) == null) state.createInitialMapData(mapName);

                BlockPos spawnpoint = user.getBlockPos();
                float yaw = Direction.fromRotation(user.getYaw()).asRotation();
                state.addSpawnpoint(mapName, spawnpoint.toCenterPos(), yaw);
                user.sendMessage(Text.of("Added spawnpoint "+spawnpoint.toShortString()+", "+yaw+" to map "+mapName), true);
                break;
        }
        state.markDirty();
        return TypedActionResult.success(stack);
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new MapNameScreenHandler(syncId, playerInventory);
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Select map to create/modify");
    }
}
