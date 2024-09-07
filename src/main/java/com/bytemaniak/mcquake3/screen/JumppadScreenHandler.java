package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.entity.JumppadEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;

public class JumppadScreenHandler extends ScreenHandler {
    public byte power;
    public JumppadEntity entity;

    protected JumppadScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    /*public JumppadScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        super(Screens.JUMPPAD_SCREEN_HANDLER, syncId);
        power = buf.readByte();
    }

    public JumppadScreenHandler(int syncId, PlayerInventory playerInventory, Object entity) {
        super(Screens.JUMPPAD_SCREEN_HANDLER, syncId);
        this.entity = (JumppadEntity) entity;
    }*/

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) { return null; }

    @Override
    public boolean canUse(PlayerEntity player) {
        return !entity.isRemoved() && player.getEyePos().distanceTo(entity.getEyePos()) < 6;
    }
}
