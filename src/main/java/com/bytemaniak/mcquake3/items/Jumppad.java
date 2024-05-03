package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.entity.JumppadEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class Jumppad extends Item {
    public Jumppad() { super(new FabricItemSettings()); }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if (!world.isClient) {
            ItemStack itemStack = context.getStack();
            Direction direction = context.getSide();
            BlockPos blockPos = context.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            BlockPos blockPos2 = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);
            PlayerEntity player = context.getPlayer();

            JumppadEntity entity = Blocks.JUMPPAD_ENTITY.spawnFromItemStack((ServerWorld) world, itemStack, player, blockPos2, SpawnReason.SPAWN_EGG, true, false);
            if (entity != null) {
                itemStack.decrement(1);
                entity.setFacing(direction);
            }
            return ActionResult.CONSUME;
        }

        return ActionResult.SUCCESS;
    }
}
