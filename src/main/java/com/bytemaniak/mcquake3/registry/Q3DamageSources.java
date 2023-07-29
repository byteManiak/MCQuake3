package com.bytemaniak.mcquake3.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;

public class Q3DamageSources {
    public static final String GAUNTLET_DAMAGE = "mcquake3.gauntlet";
    public static final String MACHINEGUN_DAMAGE = "mcquake3.machinegun";
    public static final String SHOTGUN_DAMAGE = "mcquake3.shotgun";
    public static final String GRENADE_DAMAGE = "mcquake3.grenade";
    public static final String ROCKET_DAMAGE = "mcquake3.rocket";
    public static final String LIGHTNING_DAMAGE = "mcquake3.lightning";
    public static final String RAILGUN_DAMAGE = "mcquake3.railgun";
    public static final String PLASMAGUN_DAMAGE = "mcquake3.plasmagun";
    public static final String BFG10K_DAMAGE = "mcquake3.bfg10k";

    public static final DamageSource SPIKES = new DamageSource("mcquake3.spikes");

    public static class QuakeDamageSource extends EntityDamageSource {
        public QuakeDamageSource(String name, Entity owner) {
            super(name, owner);
        }
    }
}
