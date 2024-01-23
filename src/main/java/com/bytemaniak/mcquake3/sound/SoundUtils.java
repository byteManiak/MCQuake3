package com.bytemaniak.mcquake3.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvent;

public class SoundUtils {
    @Environment(EnvType.CLIENT)
    public static void playSoundLocally(SoundEvent sound, float volume, float pitch) {
        MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(sound, pitch, volume));
    }

    @Environment(EnvType.CLIENT)
    public static void playSoundLocally(SoundEvent sound, float volume) {
        playSoundLocally(sound, volume, 1);
    }

    @Environment(EnvType.CLIENT)
    public static void playSoundLocally(SoundEvent sound) {
        playSoundLocally(sound, 1);
    }
}
