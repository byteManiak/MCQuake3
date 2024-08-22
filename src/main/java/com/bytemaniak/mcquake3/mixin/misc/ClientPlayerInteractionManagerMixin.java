package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method = "hasStatusBars", at = @At("RETURN"), cancellable = true)
    private void hideStatusBarsInQuakeArena(CallbackInfoReturnable<Boolean> ci) {
        QuakePlayer player = (QuakePlayer) MinecraftClient.getInstance().player;
        if (player.inQuakeArena()) ci.setReturnValue(false);
    }
}
