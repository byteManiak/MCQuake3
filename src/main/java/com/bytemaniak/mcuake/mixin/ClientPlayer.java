package com.bytemaniak.mcuake.mixin;

import com.bytemaniak.mcuake.MCuake;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayer extends AbstractClientPlayerEntity {
    @Shadow public Input input;
    @Shadow protected int ticksLeftToDoubleTapSprint;

    @Shadow public abstract boolean isUsingItem();

    public ClientPlayer(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "tickMovement()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;tick(ZF)V", shift = At.Shift.AFTER))
    private void cancelWeaponSlowdown(CallbackInfo ci)
    {
        if (this.isUsingItem() && !this.hasVehicle() &&
            (this.activeItemStack.isOf(MCuake.MACHINEGUN) ||
             this.activeItemStack.isOf(MCuake.PLASMAGUN)))
        {
            this.input.movementForward *= 5;
            this.input.movementSideways *= 5;
            this.ticksLeftToDoubleTapSprint = 7;
        }
    }
}
