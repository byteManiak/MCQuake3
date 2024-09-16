package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @WrapOperation(method = "renderMainHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"))
    private void cancelRenderHotbarOnQuakeMap(InGameHud hud, DrawContext context, RenderTickCounter tickCounter, Operation<Void> original) {
        if (((QuakePlayer)MinecraftClient.getInstance().player).mcquake3$inQuakeArena()) return;

        original.call(hud, context, tickCounter);
    }

    @WrapOperation(method = "renderMainHud", at =@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderExperienceBar(Lnet/minecraft/client/gui/DrawContext;I)V"))
    private void cancelExperienceBarOnQuakeMap(InGameHud hud, DrawContext context, int x, Operation<Void> original) {
        if (((QuakePlayer)MinecraftClient.getInstance().player).mcquake3$inQuakeArena()) return;

        original.call(hud, context, x);
    }

    @WrapOperation(method = "renderMainHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;hasStatusBars()Z"))
    private boolean hideStatusBarsInQuakeArena(ClientPlayerInteractionManager instance, Operation<Boolean> original) {
        QuakePlayer player = (QuakePlayer) MinecraftClient.getInstance().player;
        if (player.mcquake3$inQuakeArena()) return false;

        return original.call(instance);
    }
}
