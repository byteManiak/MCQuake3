package com.bytemaniak.mcuake.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class DamageSources {
    public static final String GAUNTLET_DAMAGE = "mcuake.gauntlet";
    public static final String MACHINEGUN_DAMAGE = "mcuake.machinegun";
    public static final String SHOTGUN_DAMAGE = "mcuake.shotgun";
    public static final String GRENADE_DAMAGE = "mcuake.grenade";
    public static final String ROCKET_DAMAGE = "mcuake.rocket";
    public static final String LIGHTNING_DAMAGE = "mcuake.lightning";
    public static final String RAILGUN_DAMAGE = "mcuake.railgun";
    public static final String PLASMAGUN_DAMAGE = "mcuake.plasmagun";
    public static final String BFG10K_DAMAGE = "mcuake.bfg10k";

    public static final DamageSource SPIKES = new DamageSource("mcuake.spikes");

    public static class QuakeDamageSource extends EntityDamageSource {
        public QuakeDamageSource(String name, Entity owner) {
            super(name, owner);
        }
    }
}
