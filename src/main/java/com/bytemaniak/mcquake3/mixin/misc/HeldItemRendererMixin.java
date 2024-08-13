package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.items.Weapon;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @WrapOperation(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/util/UseAction;"))
    // When shooting a gun, don't play the bow animation as it looks out of place
    private UseAction cancelFirstPersonBowAnimation(ItemStack instance, Operation<UseAction> original) {
        if (instance.getItem() instanceof Weapon) return UseAction.NONE;
        return original.call(instance);
    }
}
