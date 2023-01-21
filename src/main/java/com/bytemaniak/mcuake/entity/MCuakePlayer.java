package com.bytemaniak.mcuake.entity;

public interface MCuakePlayer {
    public enum Stat {
        HEALTH(0),
        ARMOR(1),
        MACHINEGUN_AMMO(2),
        SHOTGUN_AMMO(3),
        GRENADE_AMMO(4),
        ROCKET_AMMO(5),
        LIGHTNING_AMMO(6),
        RAILGUN_AMMO(7),
        PLASMAGUN_AMMO(8),
        BFG_AMMO(9);

        private final int val;

        private Stat(int val) { this.val = val; }
        public int value() { return val; }
    }
    public void takeDamage(int value);

    public void resetAmmo();
    public void resetHealth();
    public void resetArmor();

    public void killPlayer();

    public int getQuakeHealth();
    public int getQuakeArmor();
}
