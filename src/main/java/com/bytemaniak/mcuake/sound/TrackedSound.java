package com.bytemaniak.mcuake.sound;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

public class TrackedSound extends MovingSoundInstance {
    private final PlayerEntity owner;

    public TrackedSound(PlayerEntity owner, SoundEvent sound) {
        super(sound, SoundCategory.NEUTRAL, SoundInstance.createRandom());
        this.owner = owner;
    }

    @Override
    public void tick() {

    }
}
