package com.bytemaniak.mcquake3.util;

public interface QuakePlayer {
    void toggleQuakeGui();
    boolean quakeGuiEnabled();
    void setQuakeGui(boolean enabled);

    int getEnergyShield();
    void setEnergyShield(int amount);
    void addEnergyShield(int amount);

    WeaponSlot getCurrentWeapon();

    long getWeaponTick(WeaponSlot slot);
    void setWeaponTick(WeaponSlot slot, long tick);

    boolean quakePlayerSoundsEnabled();

    String getPlayerVoice();
    void setPlayerVoice(String soundsSet);

    void taunt();

    boolean isPlayingHum();
    boolean isPlayingAttack();
}
