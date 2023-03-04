package com.bytemaniak.mcuake.mixin;

import com.bytemaniak.mcuake.entity.QuakePlayer;
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
    private void noDropInventoryInQuakeMode(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        QuakePlayer thisQuakePlayer = (QuakePlayer) this;
        QuakePlayer oldQuakePlayer = (QuakePlayer) oldPlayer;
        thisQuakePlayer.setQuakeMode(oldQuakePlayer.isInQuakeMode());
        if (oldQuakePlayer.isInQuakeMode()) {
            this.getInventory().clone(oldPlayer.getInventory());
            this.experienceLevel = oldPlayer.experienceLevel;
            this.totalExperience = oldPlayer.totalExperience;
            this.experienceProgress = oldPlayer.experienceProgress;
            this.setScore(oldPlayer.getScore());
        }
    }
}
