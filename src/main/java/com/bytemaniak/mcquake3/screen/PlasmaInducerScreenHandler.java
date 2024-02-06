package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.registry.Screens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;

public class PlasmaInducerScreenHandler extends ScreenHandler {
    public PlasmaInducerScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(Screens.PLASMA_INDUCER_SCREEN_HANDLER, syncId);
    }

    public PlasmaInducerScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(Screens.PLASMA_INDUCER_SCREEN_HANDLER, syncId);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) { return null; }

    @Override
    public boolean canUse(PlayerEntity player) { return true; }
}
