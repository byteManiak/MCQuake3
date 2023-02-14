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
    @Environment(EnvType.CLIENT)
    public static void playSoundLocally(SoundEvent sound, float volume) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(sound, 1, volume));
    }

    @Environment(EnvType.CLIENT)
    public static void playSoundLocally(SoundEvent sound) {
        playSoundLocally(sound, 1);
    }
}
