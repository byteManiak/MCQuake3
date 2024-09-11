package com.bytemaniak.mcquake3.screen;

import com.bytemaniak.mcquake3.entity.JumppadEntity;
import com.bytemaniak.mcquake3.registry.Screens;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.screen.ScreenHandler;

public class JumppadScreenHandler extends ScreenHandler {
    public static final PacketCodec<RegistryByteBuf, JumppadEntity.JumppadData> CODEC = PacketCodec.tuple(
            PacketCodecs.BYTE, JumppadEntity.JumppadData::power,
            PacketCodecs.INTEGER, JumppadEntity.JumppadData::id,
            JumppadEntity.JumppadData::new
    );

    public byte power;
    public int id;

    public JumppadScreenHandler(int syncId, PlayerInventory inv, JumppadEntity.JumppadData data) {
        super(Screens.JUMPPAD_SCREEN_HANDLER, syncId);
        this.power = data.power();
        this.id = data.id();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) { return null; }

    @Override
    public boolean canUse(PlayerEntity player) {
        Entity entity = player.getWorld().getEntityById(id);
        if (entity instanceof JumppadEntity jumppad)
            return !jumppad.isRemoved() && player.getEyePos().distanceTo(jumppad.getEyePos()) < 6;

        return false;
    }
}
