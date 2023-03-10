package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.entity.projectile.Shell;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

import java.util.concurrent.ThreadLocalRandom;

public class Shotgun extends Weapon {
    private static final long SHOTGUN_REFIRE_TICK_RATE = 20;

    public Shotgun() {
        super(QuakePlayer.WeaponSlot.SHOTGUN, new Identifier("mcuake", "shotgun"),
                SHOTGUN_REFIRE_TICK_RATE, true, Sounds.SHOTGUN_FIRE, false);
    }

    private void fireProjectile(World world, LivingEntity user, int maxPitchSpread, int maxYawSpread) {
        Vec3d lookDir = Vec3d.fromPolar(user.getPitch(), user.getYaw());
        Vec3d upVec = Vec3d.fromPolar(user.getPitch() + 90, user.getYaw()).normalize();
        Vec3d rightVec = lookDir.normalize().crossProduct(upVec).normalize();
        Vec3d offsetVec = upVec.multiply(.25f);
        Vec3d spread = lookDir;
        if (maxYawSpread > 0) {
            int yawSpread = ThreadLocalRandom.current().nextInt(-maxYawSpread, maxYawSpread);
            spread = spread.add(rightVec.multiply(yawSpread/(float)maxYawSpread/4.f));
        }
        if (maxPitchSpread > 0) {
            int pitchSpread = ThreadLocalRandom.current().nextInt(-maxPitchSpread, maxPitchSpread);
            spread = spread.add(upVec.multiply(pitchSpread/(float)maxPitchSpread/6.f));
        }

        Shell shell = new Shell(world);
        shell.setOwner(user);
        shell.setPosition(user.getEyePos().add(offsetVec));
        shell.setVelocity(spread.x, spread.y, spread.z, 4.25f, 0);
        world.spawnEntity(shell);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        triggerAnim(user, GeoItem.getOrAssignId(user.getActiveItem(), (ServerWorld) world), "controller", "shoot");
        fireProjectile(world, user, 0, 0);
        for (int i = 0; i < 3; i++) fireProjectile(world, user, 7, 7);
        for (int i = 0; i < 6; i++) fireProjectile(world, user, 12, 15);
    }

    @Override
    protected PlayState handle(AnimationState<Weapon> state) {
        return null;
    }
}
