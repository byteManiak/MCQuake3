package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class Shotgun extends Weapon {
    private static final long SHOTGUN_REFIRE_TICK_RATE = 20;

    public Shotgun() { super(MCuakePlayer.WeaponSlot.SHOTGUN, SHOTGUN_REFIRE_TICK_RATE, Sounds.SHOTGUN_FIRE); }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {

    }
}
