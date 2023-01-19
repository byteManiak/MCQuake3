package com.bytemaniak.mcuake.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Machinegun extends Weapon {
    private static final long MACHINEGUN_REFIRE_TICK_RATE = 2;
    public Machinegun() {
        super(MACHINEGUN_REFIRE_TICK_RATE);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {

    }
}
