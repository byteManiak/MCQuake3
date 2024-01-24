package com.bytemaniak.mcquake3.entity;

import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.item.Item;

public interface QuakePlayer {
    enum WeaponSlot {
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

        WeaponSlot(int val, int ammoCount) {
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
                case GAUNTLET -> { return Weapons.GAUNTLET; }
                case MACHINEGUN -> { return Weapons.MACHINEGUN; }
                case SHOTGUN -> { return Weapons.SHOTGUN; }
                case GRENADE_LAUNCHER -> { return Weapons.GRENADE_LAUNCHER; }
                case ROCKET_LAUNCHER -> { return Weapons.ROCKET_LAUNCHER; }
                case LIGHTNING_GUN -> { return Weapons.LIGHTNING_GUN; }
                case RAILGUN -> { return Weapons.RAILGUN; }
                case PLASMA_GUN -> { return Weapons.PLASMAGUN; }
                case BFG10K -> { return Weapons.BFG10K; }
            }
            return net.minecraft.item.Items.AIR;
        }
    }

    void toggleQuakeGui();
    boolean quakeGuiEnabled();
    void setQuakeGui(boolean enabled);

    boolean quakePlayerSoundsEnabled();

    void resetAmmo();

    // Returns true if the player used up all the weapon ammo
    boolean useAmmo(WeaponSlot slot);
    int getCurrentAmmo();
    int getAmmo(WeaponSlot slot);
    void addAmmo(int amount, WeaponSlot slot);

    int getEnergyShield();
    void setEnergyShield(int amount);
    void addEnergyShield(int amount);

    WeaponSlot getCurrentWeapon();

    long getWeaponTick(WeaponSlot slot);
    void setWeaponTick(WeaponSlot slot, long tick);

    String getPlayerVoice();
    void setPlayerVoice(String soundsSet);

    void taunt();

    void playHum(QuakePlayer.WeaponSlot weaponSlot);
    void stopHum();

    void playAttackSound(QuakePlayer.WeaponSlot weaponSlot);
    void stopAttackSound();

    void stopSounds();

    boolean isPlayingHum();
    boolean isPlayingAttack();

    void setQuadDamage(boolean quadDamage);
    boolean hasQuadDamage();
}
