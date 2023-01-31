package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.entity.projectile.Rail;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Railgun extends Weapon{
    private static final int RAILGUN_REFIRE_TICK_RATE = 50;

    public Railgun() { super(MCuakePlayer.WeaponSlot.RAILGUN, RAILGUN_REFIRE_TICK_RATE, Sounds.RAILGUN_FIRE); }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        Vec3d lookDir = Vec3d.fromPolar(user.getPitch(), user.getYaw());
        Rail rail = new Rail(world);
        rail.setOwner(user);
        rail.setPosition(user.getEyePos());
        rail.setVelocity(lookDir.x, lookDir.y, lookDir.z, 200f, 0);
        world.spawnEntity(rail);
    }
}
