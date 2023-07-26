package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.blocks.JumppadEntity;
import com.bytemaniak.mcquake3.registry.Screens;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;

public class JumppadScreenHandler extends ScreenHandler {
    public float forward_power, up_power;
    public JumppadEntity entity;

    public JumppadScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(Screens.JUMPPAD_SCREEN_HANDLER, syncId);
    }

    public JumppadScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(Screens.JUMPPAD_SCREEN_HANDLER, syncId);
        forward_power = buf.readFloat();
        up_power = buf.readFloat();
    }

    public JumppadScreenHandler(int syncId, PlayerInventory playerInventory, JumppadEntity entity) {
        super(Screens.JUMPPAD_SCREEN_HANDLER, syncId);
        this.entity = entity;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return !this.entity.isRemoved();
    }
}
