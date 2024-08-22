package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.util.MiscUtils;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;

public class ItemEntityGotoNonHotbar extends ItemEntity {
    private int maxAmountInInventory;

    public ItemEntityGotoNonHotbar(ServerWorld world, double x, double y, double z, ItemStack stack, int maxAmountInInventory) {
        super(world, x, y, z, stack);
        this.maxAmountInInventory = maxAmountInInventory;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (getWorld().isClient) return;

        ItemStack itemStack = getStack();
        Item item = itemStack.getItem();
        int countLeft = maxAmountInInventory - MiscUtils.getCountOfItemType(player.getInventory(), item);
        int count = Math.min(itemStack.getCount(), countLeft);
        itemStack.setCount(count);

        MiscUtils.insertInNonHotbarInventory(itemStack, player.getInventory());

        player.sendPickup(this, count);
        discard();

        player.increaseStat(Stats.PICKED_UP.getOrCreateStat(item), count);
        player.triggerItemPickedUpByEntityCriteria(this);
    }
}
