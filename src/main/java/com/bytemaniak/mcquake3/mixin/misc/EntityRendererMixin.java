package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @WrapOperation(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSneaky()Z"))
    private boolean hidePlayerNamesInQuakeArena(Entity entity, Operation<Boolean> original) {
        boolean isInQuakeArena = (entity instanceof QuakePlayer player && player.inQuakeArena());
        if (isInQuakeArena) return true;

        return original.call(entity);
    }
}
