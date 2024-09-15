package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.util.QuakePlayer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @WrapOperation(method = "respawnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;copyFrom(Lnet/minecraft/server/network/ServerPlayerEntity;Z)V"))
    private void copyFromQuakePlayer(ServerPlayerEntity newPlayer, ServerPlayerEntity oldPlayer, boolean alive, Operation<Void> original) {
        QuakePlayer newQuakePlayer = (QuakePlayer) newPlayer;
        QuakePlayer oldQuakePlayer = (QuakePlayer) oldPlayer;
        newQuakePlayer.setPlayerVoice(oldQuakePlayer.getPlayerVoice());

        original.call(newPlayer, oldPlayer, alive);
    }
}
