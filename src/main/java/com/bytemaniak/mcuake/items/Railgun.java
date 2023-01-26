package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public class Railgun extends Weapon{
    private static final int RAILGUN_REFIRE_TICK_RATE = 50;

    public Railgun() { super(RAILGUN_REFIRE_TICK_RATE); }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        world.playSound(null, user.getBlockPos(), Sounds.RAILGUN_FIRE, SoundCategory.PLAYERS, .65f, 1.f);
    }
}
