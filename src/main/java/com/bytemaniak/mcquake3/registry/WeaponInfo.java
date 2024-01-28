package com.bytemaniak.mcquake3.registry;

import net.minecraft.item.Item;

public record WeaponInfo(int id, int ammoCount, int startingAmmo, Item ammoType) {
    @Override
    public int id() { return id; }

    @Override
    public int startingAmmo() { return startingAmmo; }

    @Override
    public Item ammoType() { return ammoType; }

    public static final WeaponInfo GAUNTLET = new WeaponInfo(0, 0, 0, null);
    public static final WeaponInfo MACHINEGUN = new WeaponInfo(1, 50, 100, Weapons.BULLET);
    public static final WeaponInfo SHOTGUN = new WeaponInfo(2, 10, 10, Weapons.SHELL);
    public static final WeaponInfo GRENADE_LAUNCHER = new WeaponInfo(3, 5, 10, Weapons.GRENADE);
    public static final WeaponInfo ROCKET_LAUNCHER = new WeaponInfo(4, 5, 5, Weapons.ROCKET);
    public static final WeaponInfo LIGHTNING_GUN = new WeaponInfo(5, 60, 100, null);
    public static final WeaponInfo RAILGUN = new WeaponInfo(6, 10, 10, null);
    public static final WeaponInfo PLASMA_GUN = new WeaponInfo(7, 30, 50, null);
    public static final WeaponInfo BFG10K = new WeaponInfo(8, 15, 20, null);
}
