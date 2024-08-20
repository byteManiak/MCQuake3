package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.registry.Screens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class MapNameScreenHandler extends ScreenHandler {
    public MapNameScreenHandler(int syncId, PlayerInventory inventory) {
        super(Screens.MAP_NAME_SCREEN_HANDLER, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) { return null; }

    @Override
    public boolean canUse(PlayerEntity player) { return true; }
}
