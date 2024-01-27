package com.bytemaniak.mcquake3.mixin;

import com.bytemaniak.mcquake3.util.QuakePlayer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mouse.class)
public class MouseMixin {
    @WrapOperation(method = "onMouseScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;scrollInHotbar(D)V"))
    private void skipEmptySlotInHotbar(PlayerInventory inventory, double scrollAmount, Operation<Integer> original) {
        boolean hotbarEmpty = true;
        QuakePlayer qPlayer = (QuakePlayer) MinecraftClient.getInstance().player;
        for (int i = 0; i < 9; i++)
            if (!inventory.main.get(i).isEmpty()) {
                hotbarEmpty = false;
                break;
            }

        if (hotbarEmpty || !qPlayer.quakeGuiEnabled()) original.call(inventory, scrollAmount);
        else {
            int i = (int) Math.signum(scrollAmount);
            int slot = inventory.selectedSlot - i;

            if (slot < 0) slot += 9;
            if (slot >= 9) slot -= 9;

            while (inventory.main.get(slot).isEmpty()) {
                slot -= i;
                if (slot < 0) slot += 9;
                if (slot >= 9) slot -= 9;
            }
            inventory.selectedSlot = slot;
        }
    }
}
