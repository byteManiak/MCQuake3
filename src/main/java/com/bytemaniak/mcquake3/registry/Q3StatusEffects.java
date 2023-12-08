package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.blocks.powerup.QuadDamageStatusEffect;
import com.bytemaniak.mcquake3.util.MiscUtils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Q3StatusEffects {
    public static final StatusEffect QUAD_DAMAGE = new QuadDamageStatusEffect();

    public static StatusEffectInstance fromEffect(StatusEffect effect, int amplifier) {
        return new StatusEffectInstance(effect, MiscUtils.toTicks(30), amplifier);
    }

    public static StatusEffectInstance fromEffect(StatusEffect effect) {
        return new StatusEffectInstance(effect, MiscUtils.toTicks(30), 0);
    }

    public static void registerEffects() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier("mcquake3:quad_damage"), QUAD_DAMAGE);
    }
}
