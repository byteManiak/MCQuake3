package com.bytemaniak.mcquake3.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

@Environment(EnvType.CLIENT)
public class TrackedSound extends MovingSoundInstance {
    protected final Entity owner;

    public TrackedSound(Entity owner, SoundEvent sound) {
        super(sound, SoundCategory.NEUTRAL, SoundInstance.createRandom());
        this.owner = owner;
        this.repeat = true;
    }

    @Override
    public void tick() {
        if (!owner.isAlive()) stopSound();

        x = owner.lastRenderX;
        y = owner.lastRenderY;
        z = owner.lastRenderZ;
    }

    public void stopSound() {
        setDone();
    }
}
