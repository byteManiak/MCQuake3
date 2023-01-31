package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.entity.projectile.Bullet;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Machinegun extends Weapon {
    private static final long MACHINEGUN_REFIRE_TICK_RATE = 2;
    public Machinegun() {
        super(MCuakePlayer.WeaponSlot.MACHINEGUN, MACHINEGUN_REFIRE_TICK_RATE, Sounds.MACHINEGUN_FIRE);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        // Spawn a new bullet in front of the player
        Vec3d lookDir = Vec3d.fromPolar(user.getPitch(), user.getYaw());
        Bullet bullet = new Bullet(world);
        bullet.setOwner(user);
        bullet.setPosition(user.getEyePos());
        bullet.setVelocity(lookDir.x, lookDir.y, lookDir.z, 200f, 0);
        world.spawnEntity(bullet);
    }
}
