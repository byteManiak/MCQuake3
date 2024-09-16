package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.data.QuakeArenasParameters;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.screen.ArenaBrowserScreen;
import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

public class ArenaTool extends Item {
    public ArenaTool() { super(new Settings().maxCount(1)); }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of("Used to describe properties of Quake3 arenas such as"));
        tooltip.add(Text.of("the name of the arena, and the spawnpoints located"));
        tooltip.add(Text.of("within this arena."));
        tooltip.add(Text.of("This tool will only function inside the Quake3 dimension."));
    }

    @Environment(EnvType.CLIENT)
    private void openArenaBrowser() {
        MinecraftClient.getInstance().setScreen(new ArenaBrowserScreen(Text.of("Arena browser")));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (world.isClient && !user.isSneaking()) {
            openArenaBrowser();
            return TypedActionResult.success(stack);
        }

        if (world.getDimensionKey() != Blocks.Q3_DIMENSION_TYPE) {
            ServerPlayerEntity player = (ServerPlayerEntity)user;
            ServerWorld quakeDimension = player.server.getWorld(Blocks.Q3_DIMENSION);
            player.teleport(quakeDimension, 0, 64, 0, 0, 0);
            player.changeGameMode(GameMode.CREATIVE);
            player.sendMessage(Text.of("Moved to Quake3 dimension. Have fun building arenas!"), true);

            return TypedActionResult.success(stack);
        }

        QuakePlayer player = (QuakePlayer)user;
        String arenaName = player.mcquake3$getCurrentlyEditingArena();

        if (user.isSneaking()) {
            if (arenaName.isEmpty()) {
                user.sendMessage(Text.of("No arena chosen yet"), true);
                return TypedActionResult.pass(stack);
            }

            QuakeArenasParameters state = QuakeArenasParameters.getServerState(((ServerWorld) world).getServer());
            if (state.getArena(arenaName) == null) state.createInitialArenaData(arenaName);

            BlockPos spawnpoint = user.getBlockPos();
            Direction dir = Direction.fromRotation(user.getYaw());
            float yaw = dir.asRotation();
            boolean added = state.addSpawnpoint(arenaName, spawnpoint.toCenterPos(), yaw);
            if (added)
                user.sendMessage(Text.of("Added spawnpoint "+spawnpoint.toShortString()+", "+dir.asString()+" to arena "+arenaName), true);
            else user.sendMessage(Text.of("Failed to add spawnpoint. Maybe the arena got deleted?"), true);

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.success(stack);
    }
}
