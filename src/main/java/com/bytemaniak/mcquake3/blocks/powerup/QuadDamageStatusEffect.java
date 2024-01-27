package com.bytemaniak.mcquake3.blocks.powerup;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class QuadDamageStatusEffect extends StatusEffect {
    public QuadDamageStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x00FFFF);
    }
}
