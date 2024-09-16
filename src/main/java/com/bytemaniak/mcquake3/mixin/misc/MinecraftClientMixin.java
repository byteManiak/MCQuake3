package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;doAttack()Z"))
    // Replace the attack action with the use action when firing Quake weapons
    private boolean doQuakeWeaponAttack(MinecraftClient instance, Operation<Boolean> original) {
        if (instance.player.getMainHandStack().getItem() instanceof Weapon) {
            instance.doItemUse();
            return false;
        }

        return original.call(instance);
    }

    @WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"))
    // Keep the Quake weapons firing. Replace right click check with left click check when Quake weapons are fired to ensure that.
    private boolean isQuakeWeaponFired(KeyBinding key, Operation<Boolean> original) {
        MinecraftClient instance = MinecraftClient.getInstance();
        if (key.equals(instance.options.useKey) &&
                instance.player.getActiveItem().getItem() instanceof Weapon &&
                instance.options.attackKey.isPressed())
            return true;

        return original.call(key);
    }

    @WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z"))
    private boolean cancelVanillaControlsInQuakeArena(KeyBinding key, Operation<Boolean> original) {
        MinecraftClient instance = MinecraftClient.getInstance();
        if (!((QuakePlayer)instance.player).mcquake3$inQuakeArena())
            return original.call(key);

        boolean disallowedKeyPressed = key.equals(instance.options.inventoryKey) ||
                key.equals(instance.options.dropKey) ||
                key.equals(instance.options.swapHandsKey) ||
                key.equals(instance.options.pickItemKey);

        if (disallowedKeyPressed) {
            key.wasPressed();
            return false;
        }

        return original.call(key);
    }
}
