package com.bytemaniak.mcquake3.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class Q3DamageSources {
    public static final RegistryKey<DamageType> GAUNTLET_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("mcquake3:gauntlet"));
    public static final RegistryKey<DamageType> MACHINEGUN_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("mcquake3:machinegun"));
    public static final RegistryKey<DamageType> SHOTGUN_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("mcquake3:shotgun"));
    public static final RegistryKey<DamageType> GRENADE_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("mcquake3:grenade"));
    public static final RegistryKey<DamageType> ROCKET_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("mcquake3:rocket"));
    public static final RegistryKey<DamageType> LIGHTNING_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("mcquake3:lightning"));
    public static final RegistryKey<DamageType> RAILGUN_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("mcquake3:railgun"));
    public static final RegistryKey<DamageType> PLASMAGUN_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("mcquake3:plasmagun"));
    public static final RegistryKey<DamageType> BFG10K_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("mcquake3:bfg10k"));

    public static final RegistryKey<DamageType> SPIKES = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("mcquake3:spikes"));

    public static DamageSource of(World world, RegistryKey<DamageType> type, Entity source, Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(type), source, attacker);
    }
}
