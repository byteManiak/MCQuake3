package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @ModifyVariable(method = "renderLabelIfPresent", at = @At(value = "STORE", ordinal = 0))
    private boolean hidePlayerNamesInQuakeArena(boolean hide) {
        return !((QuakePlayer)MinecraftClient.getInstance().player).inQuakeArena() && hide;
    }
}
