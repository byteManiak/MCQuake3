package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.util.MiscUtils;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;

public class ItemEntityGotoNonHotbar extends ItemEntity {
    public ItemEntityGotoNonHotbar(ServerWorld world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (getWorld().isClient) return;

        ItemStack itemStack = getStack();
        Item item = itemStack.getItem();
        int i = itemStack.getCount();
        MiscUtils.insertInNonHotbarInventory(itemStack, player.getInventory());

        player.sendPickup(this, i);
        discard();

        player.increaseStat(Stats.PICKED_UP.getOrCreateStat(item), i);
        player.triggerItemPickedUpByEntityCriteria(this);
    }
}
