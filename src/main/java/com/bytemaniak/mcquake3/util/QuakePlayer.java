package com.bytemaniak.mcquake3.util;

import com.bytemaniak.mcquake3.entity.PortalEntity;
import com.bytemaniak.mcquake3.sound.WeaponSounds;

public interface QuakePlayer extends WeaponSounds {
    boolean inQuakeArena();

    int getEnergyShield();
    void setEnergyShield(int amount);
    void addEnergyShield(int amount);

    int getCurrentQuakeWeaponId();

    long getWeaponTick(int id);
    void setWeaponTick(int id, long tick);
    boolean hasQLRefireRate();
    void setQLRefireRate(boolean hasQLRefire);
    void scrollToNextSuitableSlot();

    boolean quakePlayerSoundsEnabled();

    String getPlayerVoice();
    void setPlayerVoice(String soundsSet);

    void taunt();

    void setPortalToLink(PortalEntity entity);
    void setLinkedPortalCoords();

    void setCurrentlyEditingArena(String arenaName);
    String getCurrentlyEditingArena();
}
