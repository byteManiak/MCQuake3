package com.bytemaniak.mcuake.mixin;

import com.bytemaniak.mcuake.items.Weapon;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Redirect(method = "renderFirstPersonItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getUseAction()Lnet/minecraft/util/UseAction;"))
    // When shooting a gun, don't play the bow animation as it looks out of place
    private UseAction cancelFirstPersonBowAnimation(ItemStack instance) {
        if (instance.getItem() instanceof Weapon) {
            return UseAction.NONE;
        }
        return instance.getUseAction();
    }
}
