package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.entity.projectile.PlasmaBall;
import com.bytemaniak.mcquake3.registry.Q3StatusEffects;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;

public class Plasmagun extends Weapon {
    private static final long PLASMAGUN_REFIRE_RATE = 2;

    private static final float PLASMAGUN_PROJECTILE_SPEED = 1.5f;

    public Plasmagun() {
        super(new Identifier("mcquake3:plasmagun"), PLASMAGUN_REFIRE_RATE, PLASMAGUN_REFIRE_RATE,
                true, Sounds.PLASMAGUN_FIRE, false, Weapons.PLASMA_BALL, 50, 30, 7);
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

            if (user.hasStatusEffect(Q3StatusEffects.QUAD_DAMAGE)) plasmaBall.setQuadDamage();
            world.spawnEntity(plasmaBall);

            triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerWorld) world), "controller", "shoot");
        }
    }
}
