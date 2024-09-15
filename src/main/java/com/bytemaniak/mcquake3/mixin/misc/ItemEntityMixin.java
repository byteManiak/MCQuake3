package com.bytemaniak.mcquake3.mixin.misc;

import com.bytemaniak.mcquake3.items.ItemEntityGotoNonHotbar;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    protected ItemEntityMixin(EntityType<?> type, World world) { super(type, world); }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    private boolean discardInQuakeDimension(ItemStack stack, Operation<Boolean> original) {
        World world = getWorld();
        if (!world.isClient && world.getDimensionKey() == Blocks.Q3_DIMENSION_TYPE &&
                !((Object)this instanceof ItemEntityGotoNonHotbar))
            return true;

        return original.call(stack);
    }
}
