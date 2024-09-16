package com.bytemaniak.mcquake3.sound;

import com.bytemaniak.mcquake3.interfaces.QuakePlayer;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;

public class WeaponHum extends TrackedSound {
    protected final int id;

    public WeaponHum(Entity owner, SoundEvent sound, int id) {
        super(owner, sound);
        this.id = id;
    }

    @Override
    public void tick() {
        QuakePlayer player = (QuakePlayer) owner;
        int id = player.mcquake3$getCurrentQuakeWeaponId();
        if (id != this.id || !player.mcquake3$isPlayingHum())
            stopSound();
        super.tick();
    }
}
