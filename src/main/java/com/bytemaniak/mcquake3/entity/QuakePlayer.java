package com.bytemaniak.mcquake3.entity;

import com.bytemaniak.mcquake3.registry.Items;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;

public interface QuakePlayer {
    public enum WeaponSlot {
        GAUNTLET(0, 0),
        MACHINEGUN(1, 50),
        SHOTGUN(2, 10),
        GRENADE_LAUNCHER(3, 5),
        ROCKET_LAUNCHER(4, 5),
        LIGHTNING_GUN(5, 60),
        RAILGUN(6, 10),
        PLASMA_GUN(7, 30),
        BFG10K(8, 15),
        NONE(9, 0);

        public final int slot;
        public final int ammoCount;

        private WeaponSlot(int val, int ammoCount) {
            this.slot = val;
            this.ammoCount = ammoCount;
        }

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

        public Item toItem() {
            switch (this) {
                case GAUNTLET -> { return Items.GAUNTLET; }
                case MACHINEGUN -> { return Items.MACHINEGUN; }
                case SHOTGUN -> { return Items.SHOTGUN; }
                case GRENADE_LAUNCHER -> { return Items.GRENADE_LAUNCHER; }
                case ROCKET_LAUNCHER -> { return Items.ROCKET_LAUNCHER; }
                case LIGHTNING_GUN -> { return Items.LIGHTNING_GUN; }
                case RAILGUN -> { return Items.RAILGUN; }
                case PLASMA_GUN -> { return Items.PLASMAGUN; }
                case BFG10K -> { return Items.BFG10K; }
            }
            return net.minecraft.item.Items.AIR;
        }
    };

    public void toggleQuakeGui();
    public boolean quakeGuiEnabled();
    public void setQuakeGui(boolean enabled);

    public void toggleQuakePlayerSounds();
    public boolean quakePlayerSoundsEnabled();

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
