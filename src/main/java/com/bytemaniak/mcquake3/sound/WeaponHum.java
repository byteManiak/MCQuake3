package com.bytemaniak.mcquake3.sound;

import com.bytemaniak.mcquake3.util.QuakePlayer;
import com.bytemaniak.mcquake3.util.WeaponSlot;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;

public class WeaponHum extends TrackedSound {
    protected final WeaponSlot slot;

    public WeaponHum(Entity owner, SoundEvent sound, WeaponSlot weaponSlot) {
        super(owner, sound);
        slot = weaponSlot;
    }

    @Override
    public void tick() {
        QuakePlayer player = (QuakePlayer) owner;
        WeaponSlot weapon = player.getCurrentWeapon();
        if (weapon.slot != slot.slot || !player.isPlayingHum()) {
            stopSound();
        }
        super.tick();
    }
}
