package com.bytemaniak.mcuake.sound;

import com.bytemaniak.mcuake.entity.MCuakePlayer;

public interface LoopingWeaponSoundPlayer {
    public void playHum(MCuakePlayer.WeaponSlot weaponSlot);
    public void stopHum();

    public void playAttackSound(MCuakePlayer.WeaponSlot weaponSlot);
    public void stopAttackSound();

    public void stopSounds();
}
