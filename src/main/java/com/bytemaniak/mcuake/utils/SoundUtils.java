package com.bytemaniak.mcuake.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class SoundUtils {
    public static void playSoundAtPlayerExcept(World world, LivingEntity player, SoundEvent sound) {
        world.playSound(player, player.getBlockPos(), sound, SoundCategory.PLAYERS, .65f, 1);
    }

    @Environment(EnvType.CLIENT)
    public static void playSoundLocally(SoundEvent sound, float volume) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(sound, 1, volume));
    }

    @Environment(EnvType.CLIENT)
    public static void playSoundLocally(SoundEvent sound) {
        playSoundLocally(sound, 1);
    }

    @Environment(EnvType.CLIENT)
    public static void playSoundLocally(LivingEntity player, SoundEvent sound) {
        MinecraftClient instance = MinecraftClient.getInstance();
        if (player.getUuid().equals(instance.player.getUuid()))
            playSoundLocally(sound);
    }

    public static void playSoundAsPlayer(World world, LivingEntity player, SoundEvent sound) {
        if (world.isClient) {
            playSoundLocally(player, sound);
        } else {
            playSoundAtPlayerExcept(world, player, sound);
        }
    }
}
