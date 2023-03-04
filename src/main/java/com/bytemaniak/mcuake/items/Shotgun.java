package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.entity.projectile.Shell;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

public class Shotgun extends Weapon {
    private static final long SHOTGUN_REFIRE_TICK_RATE = 20;

    public Shotgun() { super(QuakePlayer.WeaponSlot.SHOTGUN, SHOTGUN_REFIRE_TICK_RATE, true, Sounds.SHOTGUN_FIRE, false); }

    private void fireProjectile(World world, LivingEntity user, int maxPitchSpread, int maxYawSpread) {
        int pitchSpread = ThreadLocalRandom.current().nextInt(-maxPitchSpread, Math.max(maxPitchSpread, 1));
        int yawSpread = ThreadLocalRandom.current().nextInt(-maxYawSpread, Math.max(maxYawSpread, 1));
        Vec3d lookDir = Vec3d.fromPolar(user.getPitch(), user.getYaw());
        Vec3d spread = Vec3d.fromPolar(user.getPitch() + pitchSpread, user.getYaw() + yawSpread);
        Vec3d rightVec = lookDir.crossProduct(new Vec3d(0, 1, 0));
        Vec3d upVec = lookDir.crossProduct(rightVec).normalize();
        Vec3d tempVec = lookDir.add(upVec.negate().multiply(-.0125f, 0.75f, -.0125f));
        Vec3d offsetVec = upVec.add(tempVec);
        Shell shell = new Shell(world);
        shell.setOwner(user);
        shell.setPosition(user.getEyePos().add(offsetVec));
        shell.setVelocity(spread.x, spread.y, spread.z, 4.25f, 0);
        world.spawnEntity(shell);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        fireProjectile(world, user, 0, 0);
        for (int i = 0; i < 3; i++) fireProjectile(world, user, 7, 7);
        for (int i = 0; i < 6; i++) fireProjectile(world, user, 12, 15);
    }
}
