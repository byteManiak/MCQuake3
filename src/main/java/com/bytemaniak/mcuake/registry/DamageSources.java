package com.bytemaniak.mcuake.registry;

import net.minecraft.entity.damage.DamageSource;

public class DamageSources {
    public static class QuakeDamageSource extends DamageSource {
        public QuakeDamageSource(String name) {
            super(name);
        }
    }

    public static final QuakeDamageSource GAUNTLET_DAMAGE = new QuakeDamageSource("mcuake.gauntlet");
    public static final QuakeDamageSource MACHINEGUN_DAMAGE = new QuakeDamageSource("mcuake.machinegun");
    public static final QuakeDamageSource SHOTGUN_DAMAGE = new QuakeDamageSource("mcuake.shotgun");
    public static final QuakeDamageSource GRENADE_DAMAGE = new QuakeDamageSource("mcuake.grenade");
    public static final QuakeDamageSource ROCKET_DAMAGE = new QuakeDamageSource("mcuake.rocket");
    public static final QuakeDamageSource LIGHTNING_DAMAGE = new QuakeDamageSource("mcuake.lightning");
    public static final QuakeDamageSource RAILGUN_DAMAGE = new QuakeDamageSource("mcuake.railgun");
    public static final QuakeDamageSource PLASMAGUN_DAMAGE = new QuakeDamageSource("mcuake.plasmagun");
    public static final QuakeDamageSource BFG10K_DAMAGE = new QuakeDamageSource("mcuake.bfg10k");

    public static final QuakeDamageSource SPIKES = new QuakeDamageSource("mcuake.spikes");
}
