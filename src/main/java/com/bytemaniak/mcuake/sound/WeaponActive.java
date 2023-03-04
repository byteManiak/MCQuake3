package com.bytemaniak.mcuake.sound;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;

public class WeaponActive extends TrackedSound {
    public WeaponActive(Entity owner, SoundEvent sound, QuakePlayer.WeaponSlot weaponSlot) {
        super(owner, sound, weaponSlot);
    }

    @Override
    public void tick() {
        QuakePlayer player = (QuakePlayer) owner;
        QuakePlayer.WeaponSlot weapon = player.getCurrentWeapon();
        if (weapon.slot() != this.slot.slot() || !player.isPlayingAttack()) {
            setDone();
        }
        super.tick();
    }
}
