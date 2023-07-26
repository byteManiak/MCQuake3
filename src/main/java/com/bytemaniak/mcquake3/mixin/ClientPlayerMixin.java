package com.bytemaniak.mcquake3.mixin;

import com.bytemaniak.mcquake3.items.Weapon;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerMixin extends AbstractClientPlayerEntity {
    public ClientPlayerMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean cancelWeaponSlowdown(ClientPlayerEntity instance) {
        if (instance.getActiveItem().getItem() instanceof Weapon) return false;
        else return instance.isUsingItem();
    }
}
