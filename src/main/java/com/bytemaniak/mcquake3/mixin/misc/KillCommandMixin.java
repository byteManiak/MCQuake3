package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.util.QuakePlayer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.KillCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KillCommand.class)
public class KillCommandMixin {
    @WrapOperation(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;kill()V"))
    private static void cancelKillCommandInQuakeArena(Entity entity, Operation<Void> original) {
        if (entity instanceof QuakePlayer qPlayer && qPlayer.inQuakeArena()) {
            PlayerEntity player = (PlayerEntity)entity;
            entity.damage(entity.getDamageSources().outOfWorld(), player.getMaxHealth());
        } else original.call(entity);
    }
}
