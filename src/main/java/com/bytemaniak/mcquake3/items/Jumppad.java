package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.entity.JumppadEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;

public class Jumppad extends Item {
    public Jumppad() { super(new Item.Settings()); }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of("Change orientation and strength"));
        tooltip.add(Text.of("by using the [MCQuake3 Tool]."));
    }

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
