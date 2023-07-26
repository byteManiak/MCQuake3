package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import com.bytemaniak.mcquake3.entity.projectile.PlasmaBall;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class Plasmagun extends Weapon {
    private static final long PLASMAGUN_REFIRE_TICK_RATE = 2;

    private static final float PLASMAGUN_PROJECTILE_SPEED = 1.5f;

    public Plasmagun() {
        super(QuakePlayer.WeaponSlot.PLASMA_GUN, new Identifier("mcquake3", "plasmagun"),
                PLASMAGUN_REFIRE_TICK_RATE, true, Sounds.PLASMAGUN_FIRE, false);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack, Vec3d lookDir, Vec3d weaponPos) {
        if (!world.isClient) {
            // Spawn a new plasma ball approximately from the weapon
            Vec3d upVec = Vec3d.fromPolar(user.getPitch() + 90, user.getYaw()).normalize();
            Vec3d rightVec = lookDir.crossProduct(upVec).normalize();
            Vec3d offsetWeaponPos = weaponPos
                    .add(upVec.multiply(PROJECTILE_VERTICAL_SPAWN_OFFSET))
                    .add(rightVec.multiply(PROJECTILE_HORIZONTAL_SPAWN_OFFSET))
                    .add(lookDir.multiply(PROJECTILE_FORWARD_SPAWN_OFFSET));

            // The furthest point, to which the projectile will go towards
            Vec3d destPos = user.getEyePos().add(lookDir.multiply(PROJECTILE_DIRECTION_RANGE));
            Vec3d destDir = destPos.subtract(offsetWeaponPos).normalize();

            PlasmaBall plasmaBall = new PlasmaBall(world);
            plasmaBall.setOwner(user);
            plasmaBall.setPosition(offsetWeaponPos);
            plasmaBall.setVelocity(destDir.x, destDir.y, destDir.z, PLASMAGUN_PROJECTILE_SPEED, 0);
            world.spawnEntity(plasmaBall);

            triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerWorld) world), "controller", "shoot");
        }
    }

    @Override
    protected PlayState handle(AnimationState<Weapon> state) {
        return null;
    }
}
