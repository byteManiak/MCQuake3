package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.entity.projectile.Rocket;
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
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;

public class RocketLauncher extends Weapon {
    private static final long ROCKET_REFIRE_RATE_Q3 = 15;
    private static final long ROCKET_REFIRE_RATE_QL = 16;
    private static final float ROCKET_PROJECTILE_SPEED = 1.5f;
    /* Spawn rockets from the centre to allow proper rocket jumps */
    private static final float ROCKET_HORIZONTAL_SPAWN_OFFSET = .2f;

    public RocketLauncher() {
        super(new Identifier("mcquake3:rocket_launcher"), ROCKET_REFIRE_RATE_Q3, ROCKET_REFIRE_RATE_QL,
                true, Sounds.GRENADE_FIRE, false, Weapons.ROCKET, 5, 5, 4);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack, Vec3d lookDir, Vec3d weaponPos) {
        if (!world.isClient) {
            // Spawn a new rocket approximately from the weapon
            Vec3d upVec = Vec3d.fromPolar(user.getPitch() + 90, user.getYaw()).normalize();
            Vec3d rightVec = lookDir.crossProduct(upVec).normalize();
            Vec3d offsetWeaponPos = weaponPos
                    .add(upVec.multiply(PROJECTILE_VERTICAL_SPAWN_OFFSET))
                    .add(rightVec.multiply(ROCKET_HORIZONTAL_SPAWN_OFFSET))
                    .add(lookDir.multiply(PROJECTILE_FORWARD_SPAWN_OFFSET));

            // The furthest point, to which the projectile will go towards
            Vec3d destPos = user.getEyePos().add(lookDir.multiply(PROJECTILE_DIRECTION_RANGE));
            Vec3d destDir = destPos.subtract(offsetWeaponPos).normalize();

            Rocket rocket = new Rocket(world);
            rocket.setOwner(user);
            rocket.setPosition(offsetWeaponPos);
            rocket.setVelocity(destDir.x, destDir.y, destDir.z, ROCKET_PROJECTILE_SPEED, 0);

            if (user.hasStatusEffect(Q3StatusEffects.QUAD_DAMAGE)) rocket.setQuadDamage();
            world.spawnEntity(rocket);

            triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerWorld) world), "controller", "shoot");
        }
    }
}
