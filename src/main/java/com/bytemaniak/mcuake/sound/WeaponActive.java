package com.bytemaniak.mcuake.sound;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundEvent;

public class WeaponActive extends TrackedSound {
    public WeaponActive(Entity owner, SoundEvent sound, MCuakePlayer.WeaponSlot weaponSlot) {
        super(owner, sound, weaponSlot);
    }

    @Override
    public void tick() {
        MCuakePlayer player = (MCuakePlayer) owner;
        MCuakePlayer.WeaponSlot weapon = player.getCurrentWeapon();
        if (weapon.slot() != this.slot.slot() || !player.isPlayingAttack()) {
            setDone();
        }
        super.tick();
    }
}
