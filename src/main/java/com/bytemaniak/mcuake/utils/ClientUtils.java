package com.bytemaniak.mcuake.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;

public class ClientUtils {
    public static void playSoundPositioned(Entity entity, SoundEvent sound) {
        MinecraftClient instance = MinecraftClient.getInstance();
        if (entity.getUuid() == instance.player.getUuid())
            instance.getSoundManager().play(PositionedSoundInstance.master(sound, 1));
    }
}
