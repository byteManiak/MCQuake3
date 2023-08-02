package com.bytemaniak.mcquake3.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @WrapOperation(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean ignoreInvulnerabilityFrames(DamageSource damageSource, TagKey<DamageType> damageType, Operation<Boolean> original) {
        // The code in LivingEntity has multiple isIn() checks in the damage() function
        // The !BYPASSES_COOLDOWN check is used to override the invulnerability frames
        // The !IS_EXPLOSION check is used to apply knockback to the entity when hurt
        if (damageSource.getName().contains("mcquake3") &&
                (damageType == DamageTypeTags.BYPASSES_COOLDOWN ||
                 damageType == DamageTypeTags.IS_EXPLOSION)) {
            return true;
        } else return original.call(damageSource, damageType);
    }
}
