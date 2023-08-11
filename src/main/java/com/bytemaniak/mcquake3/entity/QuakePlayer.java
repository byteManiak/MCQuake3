package com.bytemaniak.mcquake3.entity;

import net.minecraft.entity.damage.DamageSource;

public interface QuakePlayer {
    public enum WeaponSlot {
        GAUNTLET(0),
        MACHINEGUN(1),
        SHOTGUN(2),
        GRENADE_LAUNCHER(3),
        ROCKET_LAUNCHER(4),
        LIGHTNING_GUN(5),
        RAILGUN(6),
        PLASMA_GUN(7),
        BFG10K(8),
        NONE(9);

        private final int slot;
        private WeaponSlot(int val) { this.slot = val; }
        public int slot() { return slot; }

        @Override
        public String toString() {
            switch (this) {
                case GAUNTLET -> { return "Gauntlet"; }
                case MACHINEGUN -> { return "Machinegun"; }
                case SHOTGUN -> { return "Shotgun"; }
                case GRENADE_LAUNCHER -> { return "Grenade Launcher"; }
                case ROCKET_LAUNCHER -> { return "Rocket Launcher"; }
                case LIGHTNING_GUN -> { return "Lightning Gun"; }
                case RAILGUN -> { return "Railgun"; }
                case PLASMA_GUN -> { return "Plasmagun"; }
                case BFG10K -> { return "BFG10K"; }
            }
            return "";
        }
    };

    public void toggleQuakeGui();
    public boolean quakeGuiEnabled();
    public void setQuakeGui(boolean enabled);

    public void toggleQuakePlayerSounds();
    public boolean quakePlayerSoundsEnabled();
    public void setQuakePlayerSoundsEnabled(boolean enabled);

    public void resetAmmo();

    // Returns true if the player used up all the weapon ammo
    public boolean useAmmo(WeaponSlot slot);
    public int getCurrentAmmo();
    public int getAmmo(WeaponSlot slot);
    public void addAmmo(int amount, WeaponSlot slot);

    public WeaponSlot getCurrentWeapon();

    public void takeDamage(int amount, DamageSource damageSource);

    public long getWeaponTick(WeaponSlot slot);
    public void setWeaponTick(WeaponSlot slot, long tick);

    public String getPlayerVoice();
    public void setPlayerVoice(String soundsSet);

    public void playHum(QuakePlayer.WeaponSlot weaponSlot);
    public void stopHum();

    public void playAttackSound(QuakePlayer.WeaponSlot weaponSlot);
    public void stopAttackSound();

    public void stopSounds();

    public boolean isPlayingHum();
    public boolean isPlayingAttack();
}
