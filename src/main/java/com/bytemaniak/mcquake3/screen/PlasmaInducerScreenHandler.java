package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.recipes.PlasmaInducerRecipe;
import com.bytemaniak.mcquake3.registry.Screens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;

public class PlasmaInducerScreenHandler extends AbstractFurnaceScreenHandler {
    public PlasmaInducerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(Screens.PLASMA_INDUCER_SCREEN_HANDLER, PlasmaInducerRecipe.PlasmaInducerType.INSTANCE, RecipeBookCategory.CRAFTING,
                syncId, playerInventory, inventory, propertyDelegate);

        slots.clear();
        trackedStacks.clear();
        previousTrackedStacks.clear();

        addSlot(new Slot(inventory, 0, 65, 47));
        addSlot(new FurnaceFuelSlot(this, inventory, 1, 65, 83));
        addSlot(new FurnaceOutputSlot(playerInventory.player, inventory, 2, 125, 65));
        addSlot(new Slot(inventory, 3, 37, 30));
        addSlot(new Slot(inventory, 4, 65, 18));
        addSlot(new Slot(inventory, 5, 93, 30));

        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 111 + i * 18));

        for (int i = 0; i < 9; ++i) addSlot(new Slot(playerInventory, i, 8 + i * 18, 169));
    }

    public PlasmaInducerScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(4));
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < 6) {
                if (!this.insertItem(itemStack2, 6, 42, true)) return ItemStack.EMPTY;
                if (slot == 2) slot2.onQuickTransfer(itemStack2, itemStack);
            } else if (isSmeltable(itemStack2)) {
                if (!insertItem(itemStack2, 0, 1, false) && !insertItem(itemStack2, 3, 6, false))
                    return ItemStack.EMPTY;
            } else if (isFuel(itemStack2)) {
                if (!insertItem(itemStack2, 1, 2, false)) return ItemStack.EMPTY;
            } else if (slot < 33) {
                if (!insertItem(itemStack2, 33, 42, false)) return ItemStack.EMPTY;
            } else if (slot < 42) {
                if (!insertItem(itemStack2, 6, 33, false)) return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) slot2.setStack(ItemStack.EMPTY);
            else slot2.markDirty();

            if (itemStack2.getCount() == itemStack.getCount())
                return ItemStack.EMPTY;

            slot2.onTakeItem(player, itemStack2);
        }
        return itemStack;
    }

    @Override
    protected boolean isFuel(ItemStack itemStack) { return itemStack.isOf(Items.LAVA_BUCKET); }

    @Override
    public void clearCraftingSlots() {
        this.getSlot(0).setStackNoCallbacks(ItemStack.EMPTY);
        this.getSlot(2).setStackNoCallbacks(ItemStack.EMPTY);
        this.getSlot(3).setStackNoCallbacks(ItemStack.EMPTY);
        this.getSlot(4).setStackNoCallbacks(ItemStack.EMPTY);
        this.getSlot(5).setStackNoCallbacks(ItemStack.EMPTY);
    }
}
