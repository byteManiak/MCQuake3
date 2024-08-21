package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.data.QuakeMapsParameters;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.screen.MapBrowserScreen;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MapTool extends Item {
    public MapTool() {
        super(new Settings().maxCount(1));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of("Used to describe properties of Quake3 maps such as"));
        tooltip.add(Text.of("the name of the map, and the spawnpoints located"));
        tooltip.add(Text.of("within this map."));
        tooltip.add(Text.of("This tool will only function inside the Quake3 dimension."));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient && !user.isSneaking()) {
            MinecraftClient.getInstance().setScreen(new MapBrowserScreen(Text.of("Map browser")));
            return TypedActionResult.success(stack);
        }

        if (world.getDimensionKey() != Blocks.Q3_DIMENSION_TYPE) {
            ServerPlayerEntity player = (ServerPlayerEntity)user;
            ServerWorld quakeDimension = player.server.getWorld(Blocks.Q3_DIMENSION);
            player.teleport(quakeDimension, 0, 64, 0, 0, 0);
            player.changeGameMode(GameMode.CREATIVE);
            player.sendMessage(Text.of("Moved to Quake3 dimension. Have fun building maps!"), true);

            return TypedActionResult.success(stack);
        }

        QuakePlayer player = (QuakePlayer)user;
        String mapName = player.getCurrentlyEditingMap();

        if (user.isSneaking()) {
            if (mapName.isEmpty()) {
                user.sendMessage(Text.of("No map chosen yet"), true);
                return TypedActionResult.pass(stack);
            }

            QuakeMapsParameters state = QuakeMapsParameters.getServerState(((ServerWorld) world).getServer());
            if (state.getMap(mapName) == null) state.createInitialMapData(mapName);

            BlockPos spawnpoint = user.getBlockPos();
            float yaw = Direction.fromRotation(user.getYaw()).asRotation();
            state.addSpawnpoint(mapName, spawnpoint.toCenterPos(), yaw);
            user.sendMessage(Text.of("Added spawnpoint "+spawnpoint.toShortString()+", "+yaw+" to map "+mapName), true);

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.success(stack);
    }
}
