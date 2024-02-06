package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.registry.RecipeTypes;
import com.bytemaniak.mcquake3.registry.Screens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;

public class PlasmaInducerScreenHandler extends AbstractFurnaceScreenHandler {
    public PlasmaInducerScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(Screens.PLASMA_INDUCER_SCREEN_HANDLER, RecipeTypes.PLASMA_INDUCER_RECIPE_TYPE, RecipeBookCategory.CRAFTING, syncId, playerInventory);
    }

    public PlasmaInducerScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) { return null; }

    @Override
    public boolean canUse(PlayerEntity player) { return true; }
}
