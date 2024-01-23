package com.bytemaniak.mcquake3.blocks.powerup;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class QuadDamageStatusEffect extends StatusEffect {
    public QuadDamageStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x00FFFF);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof QuakePlayer quakePlayer) {
            quakePlayer.setQuadDamage(true);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof QuakePlayer quakePlayer) {
            quakePlayer.setQuadDamage(false);
        }
    }
}
