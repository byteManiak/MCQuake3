package com.bytemaniak.mcquake3.mixin;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin extends PlayerEntity {
    public ServerPlayerMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "copyFrom", at = @At("HEAD"))
    private void copyFromQuakePlayer(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        QuakePlayer thisQuakePlayer = (QuakePlayer) this;
        QuakePlayer oldQuakePlayer = (QuakePlayer) oldPlayer;
        thisQuakePlayer.setQuakeGui(oldQuakePlayer.quakeGuiEnabled());
        thisQuakePlayer.setQuakePlayerSoundsEnabled(oldQuakePlayer.quakePlayerSoundsEnabled());

        // Don't remove inventory and XP if in Quake mode
        if (oldQuakePlayer.quakeGuiEnabled()) {
            this.getInventory().clone(oldPlayer.getInventory());
            this.experienceLevel = oldPlayer.experienceLevel;
            this.totalExperience = oldPlayer.totalExperience;
            this.experienceProgress = oldPlayer.experienceProgress;
            this.setScore(oldPlayer.getScore());
        }

        thisQuakePlayer.setPlayerVoice(oldQuakePlayer.getPlayerVoice());
    }
}
