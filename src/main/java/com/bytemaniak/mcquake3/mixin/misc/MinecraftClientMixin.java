package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.items.Weapon;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "doAttack", at = @At(value = "HEAD"), cancellable = true)
    // Replace the attack action with the use action when firing Quake weapons
    private void doQuakeWeaponAttack(CallbackInfoReturnable<Void> cir) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player.getMainHandStack().getItem() instanceof Weapon) {
            client.doItemUse();
            cir.cancel();
        }
    }

    @WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"))
    // Keep the Quake weapons firing. Replace right click check with left click check when Quake weapons are fired to ensure that.
    private boolean isQuakeWeaponFired(KeyBinding key, Operation<Boolean> original) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (key.getTranslationKey().equals("key.use") &&
                client.player.getActiveItem().getItem() instanceof Weapon &&
                client.options.attackKey.isPressed()) {
            return true;
        }
        return original.call(key);
    }
}
