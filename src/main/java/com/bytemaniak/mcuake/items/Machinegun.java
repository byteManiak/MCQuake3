package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Sounds;

public class Machinegun extends HitscanWeapon {
    private static final long MACHINEGUN_REFIRE_TICK_RATE = 2;
    private static final int MACHINEGUN_QUAKE_DAMAGE = 7;
    private static final int MACHINEGUN_MC_DAMAGE = 2;
    private static final float MACHINEGUN_RANGE = 200;

    public Machinegun() {
        super(QuakePlayer.WeaponSlot.MACHINEGUN, MACHINEGUN_REFIRE_TICK_RATE, true, Sounds.MACHINEGUN_FIRE, false,
                MACHINEGUN_QUAKE_DAMAGE, MACHINEGUN_MC_DAMAGE, DamageSources.MACHINEGUN_DAMAGE, MACHINEGUN_RANGE);
    }
}
