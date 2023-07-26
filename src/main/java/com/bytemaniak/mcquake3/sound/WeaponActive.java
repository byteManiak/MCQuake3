package com.bytemaniak.mcquake3.sound;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;

public class WeaponActive extends TrackedSound {
    protected final QuakePlayer.WeaponSlot slot;

    public WeaponActive(Entity owner, SoundEvent sound, QuakePlayer.WeaponSlot weaponSlot) {
        super(owner, sound);
        slot = weaponSlot;
    }

    @Override
    public void tick() {
        QuakePlayer player = (QuakePlayer) owner;
        QuakePlayer.WeaponSlot weapon = player.getCurrentWeapon();
        if (weapon.slot() != this.slot.slot() || !player.isPlayingAttack()) {
            stopSound();
        }
        super.tick();
    }
}
