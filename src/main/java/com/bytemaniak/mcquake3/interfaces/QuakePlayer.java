package com.bytemaniak.mcquake3.interfaces;

import com.bytemaniak.mcquake3.entity.PortalEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;

public interface QuakePlayer {
    boolean mcquake3$inQuakeArena();

    int mcquake3$getEnergyShield();
    void mcquake3$setEnergyShield(int amount);
    void mcquake3$addEnergyShield(int amount);

    int mcquake3$getCurrentQuakeWeaponId();

    long mcquake3$getWeaponTick(int id);
    void mcquake3$setWeaponTick(int id, long tick);
    boolean mcquake3$hasQLRefireRate();
    void mcquake3$setQLRefireRate(boolean hasQLRefire);
    void mcquake3$scrollToNextSuitableSlot();

    boolean mcquake3$quakePlayerSoundsEnabled();

    String mcquake3$getPlayerVoice();
    void mcquake3$setPlayerVoice(String soundsSet);
    SoundEvent mcquake3$getPlayerDeathSound();
    SoundEvent mcquake3$getPlayerHurtSound(DamageSource source);

    void mcquake3$taunt();

    void mcquake3$setPortalToLink(PortalEntity entity);
    void mcquake3$setLinkedPortalCoords();

    void mcquake3$setCurrentlyEditingArena(String arenaName);
    String mcquake3$getCurrentlyEditingArena();

    boolean mcquake3$isPlayingHum();
    boolean mcquake3$isPlayingAttack();

    boolean mcquake3$hasQuadDamage();
}
