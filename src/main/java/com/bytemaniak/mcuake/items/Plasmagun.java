package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.entity.projectile.PlasmaBall;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Plasmagun extends Weapon {
    private static final long PLASMAGUN_REFIRE_TICK_RATE = 2;

    public Plasmagun() {
        super(QuakePlayer.WeaponSlot.PLASMA_GUN, PLASMAGUN_REFIRE_TICK_RATE, true, Sounds.PLASMAGUN_FIRE, false);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        // Spawn a new plasma ball in front of the player
        Vec3d lookDir = Vec3d.fromPolar(user.getPitch(), user.getYaw());
        Vec3d rightVec = lookDir.crossProduct(new Vec3d(0, 1, 0));
        Vec3d upVec = lookDir.crossProduct(rightVec).normalize();
        Vec3d tempVec = lookDir.add(upVec.negate().multiply(-.0125f, 0.35f, -.0125f));
        Vec3d offsetVec = upVec.add(tempVec);
        PlasmaBall plasmaBall = new PlasmaBall(world);
        plasmaBall.setOwner(user);
        plasmaBall.setPosition(user.getEyePos().add(offsetVec));
        plasmaBall.setVelocity(lookDir.x, lookDir.y, lookDir.z, 1.5f, 0);
        world.spawnEntity(plasmaBall);
    }
}
