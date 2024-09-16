package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(FLnet/minecraft/client/util/math/MatrixStack;)V"))
    private void cancelRenderHotbarInQuakeArena(InGameHud hud, float tickDelta, MatrixStack matrices, Operation<Void> original) {
        if (((QuakePlayer)MinecraftClient.getInstance().player).mcquake3$inQuakeArena()) return;

        original.call(hud, tickDelta, matrices);
    }

    @WrapOperation(method = "render", at =@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderExperienceBar(Lnet/minecraft/client/util/math/MatrixStack;I)V"))
    private void cancelExperienceBarInQuakeArena(InGameHud hud, MatrixStack matrices, int x, Operation<Void> original) {
        if (((QuakePlayer)MinecraftClient.getInstance().player).mcquake3$inQuakeArena()) return;

        original.call(hud, matrices, x);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;hasStatusBars()Z"))
    private boolean hideStatusBarsInQuakeArena(ClientPlayerInteractionManager instance, Operation<Boolean> original) {
        QuakePlayer player = (QuakePlayer) MinecraftClient.getInstance().player;
        if (player.mcquake3$inQuakeArena()) return false;

        return original.call(instance);
    }
}
