package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.projectile.Bullet;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Machinegun extends Weapon {
    private static final long MACHINEGUN_REFIRE_TICK_RATE = 2;
    public Machinegun() {
        super(MACHINEGUN_REFIRE_TICK_RATE);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        // Spawn a new bullet in front of the player
        Vec3d lookDir = Vec3d.fromPolar(user.getPitch(), user.getYaw());
        Vec3d rightVec = lookDir.crossProduct(new Vec3d(0, 1, 0));
        Vec3d upVec = lookDir.crossProduct(rightVec).normalize();
        Vec3d tempVec = lookDir.add(upVec.negate().multiply(0.f, 0.15f, 0.f));
        Vec3d offsetVec = upVec.add(tempVec);
        Bullet bullet = new Bullet(world);
        bullet.setOwner(user);
        bullet.setPosition(user.getEyePos().add(offsetVec));
        bullet.setVelocity(user.getPitch(), user.getYaw(), 10.f);
        bullet.setVelocity(lookDir.x, lookDir.y, lookDir.z, 15f, 0);
        world.spawnEntity(bullet);
        world.playSound(null, user.getBlockPos(), Sounds.MACHINEGUN_FIRE, SoundCategory.PLAYERS, .65f, 1);
    }
}
