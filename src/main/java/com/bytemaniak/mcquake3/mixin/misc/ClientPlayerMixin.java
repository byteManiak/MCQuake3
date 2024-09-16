package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.items.Weapon;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerMixin {
    @WrapOperation(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    // Don't slow the player down if they are firing a Quake weapon.
    private boolean cancelWeaponSlowdown(ClientPlayerEntity instance, Operation<Boolean> original) {
        if (instance.getActiveItem().getItem() instanceof Weapon) return false;
        else return original.call(instance);
    }

    @WrapOperation(method = "updateHealth", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hurtTime:I", opcode = Opcodes.PUTFIELD))
    // Don't show the vanilla damage hurt tilt when damaged by Quake weapons as some of them fire multiple times per second
    private void cancelHurtTilt(ClientPlayerEntity entity, int value, Operation<Void> original) {
        DamageSource lastDamageSource = entity.getRecentDamageSource();
        if (lastDamageSource != null && lastDamageSource.getName().contains("mcquake3"))
            entity.hurtTime = 0;
        else original.call(entity, value);
    }
}
