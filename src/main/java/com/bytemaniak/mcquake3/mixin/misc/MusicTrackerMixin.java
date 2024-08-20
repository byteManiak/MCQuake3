package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.sound.MusicSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MusicTracker.class)
public abstract class MusicTrackerMixin {
    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getMusicType()Lnet/minecraft/sound/MusicSound;"))
    private MusicSound playQuakeMusic(MinecraftClient instance, Operation<MusicSound> original) {
        QuakePlayer player = (QuakePlayer) instance.player;
        if (player != null && player.playingQuakeMap() &&
                instance.interactionManager.getCurrentGameMode().isSurvivalLike()) return Sounds.Q3_MUSIC;

        return original.call(instance);
    }
}
