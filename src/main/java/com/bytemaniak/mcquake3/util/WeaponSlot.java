package com.bytemaniak.mcquake3.util;

import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

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

    WeaponSlot(int val, int ammoCount) {
        this.slot = val;
        this.ammoCount = ammoCount;
    }

    @Override
    public String toString() {
        return switch (this) {
            case GAUNTLET -> "Gauntlet";
            case MACHINEGUN -> "Machinegun";
            case SHOTGUN -> "Shotgun";
            case GRENADE_LAUNCHER -> "Grenade Launcher";
            case ROCKET_LAUNCHER -> "Rocket Launcher";
            case LIGHTNING_GUN -> "Lightning Gun";
            case RAILGUN -> "Railgun";
            case PLASMA_GUN -> "Plasmagun";
            case BFG10K -> "BFG10K";
            default -> "";
        };
    }

    public Item toItem() {
        return switch (this) {
            case GAUNTLET -> Weapons.GAUNTLET;
            case MACHINEGUN -> Weapons.MACHINEGUN;
            case SHOTGUN -> Weapons.SHOTGUN;
            case GRENADE_LAUNCHER -> Weapons.GRENADE_LAUNCHER;
            case ROCKET_LAUNCHER -> Weapons.ROCKET_LAUNCHER;
            case LIGHTNING_GUN -> Weapons.LIGHTNING_GUN;
            case RAILGUN -> Weapons.RAILGUN;
            case PLASMA_GUN -> Weapons.PLASMAGUN;
            case BFG10K -> Weapons.BFG10K;
            default -> Items.AIR;
        };
    }
}
