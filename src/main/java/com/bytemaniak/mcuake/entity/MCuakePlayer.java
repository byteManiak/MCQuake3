package com.bytemaniak.mcuake.entity;

import net.minecraft.entity.damage.DamageSource;

public interface MCuakePlayer {
    public enum WeaponSlot {
        GAUNTLET(0),
        MACHINEGUN(1),
        SHOTGUN(2),
        GRENADE_LAUNCHER(3),
        ROCKET_LAUNCHER(4),
        LIGHTNING_GUN(5),
        RAILGUN(6),
        PLASMA_GUN(7),
        BFG10K(8);

        private final int slot;
        private WeaponSlot(int val) { this.slot = val; }
        public int slot() { return slot; }
    };

    public void setQuakeHealth(int amount);
    public void setQuakeArmor(int amount);

    public int getQuakeHealth();
    public int getQuakeArmor();

    public void resetAmmo();

    public void takeDamage(int amount, DamageSource damageSource);

    public long getWeaponTick(WeaponSlot slot, boolean clientside);
    public void setWeaponTick(WeaponSlot slot, long tick, boolean clientside);
}
