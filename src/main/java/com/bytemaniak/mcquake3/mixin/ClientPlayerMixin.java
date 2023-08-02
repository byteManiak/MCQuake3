package com.bytemaniak.mcquake3.mixin;

import com.bytemaniak.mcquake3.items.Weapon;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.damage.DamageSource;
import org.objectweb.asm.Opcodes;
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

    @Redirect(method = "updateHealth", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hurtTime:I", opcode = Opcodes.PUTFIELD))
    private void cancelHurtTilt(ClientPlayerEntity entity, int value) {
        DamageSource lastDamageSource = entity.getRecentDamageSource();
        if (lastDamageSource != null && lastDamageSource.getName().contains("mcquake3"))
            entity.hurtTime = entity.maxHurtTime = 0;
        else entity.hurtTime = entity.maxHurtTime = value;
    }
}
