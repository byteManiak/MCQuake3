package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.util.MiscUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class Q3StatusEffects {
    public static final StatusEffectInstance SPEED_STATUS_EFFECT =
            new StatusEffectInstance(StatusEffects.SPEED, MiscUtils.toTicks(30), 2);
    public static final StatusEffectInstance HASTE_STATUS_EFFECT =
            new StatusEffectInstance(StatusEffects.HASTE, MiscUtils.toTicks(30), 3);
    public static final StatusEffectInstance INVISIBILITY_STATUS_EFFECT =
            new StatusEffectInstance(StatusEffects.INVISIBILITY, MiscUtils.toTicks(30));
}
