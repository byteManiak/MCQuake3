package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.entity.projectile.Grenade;
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

public class GrenadeLauncher extends Weapon {
    private static final long GRENADE_REFIRE_RATE_Q3 = 15;
    private static final long GRENADE_REFIRE_RATE_QL = 16;
    private static final float GRENADE_PROJECTILE_SPEED = .85f;

    public GrenadeLauncher() {
        super(new Identifier("mcquake3:grenade_launcher"), GRENADE_REFIRE_RATE_Q3, GRENADE_REFIRE_RATE_QL,
                true, Sounds.GRENADE_FIRE, false, Weapons.GRENADE, 10, 5, 3);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack, Vec3d lookDir, Vec3d weaponPos) {
        if (!world.isClient) {
            // Spawn a new grenade approximately from the weapon
            Vec3d upVec = Vec3d.fromPolar(user.getPitch() + 90, user.getYaw()).normalize();
            Vec3d rightVec = lookDir.crossProduct(upVec).normalize();
            Vec3d offsetWeaponPos = weaponPos
                    .add(upVec.multiply(PROJECTILE_VERTICAL_SPAWN_OFFSET))
                    .add(rightVec.multiply(PROJECTILE_HORIZONTAL_SPAWN_OFFSET))
                    .add(lookDir.multiply(PROJECTILE_FORWARD_SPAWN_OFFSET));

            // The furthest point, to which the projectile will go towards
            Vec3d destPos = user.getEyePos().add(lookDir.multiply(PROJECTILE_DIRECTION_RANGE));
            Vec3d destDir = destPos.subtract(offsetWeaponPos).subtract(upVec.multiply(20)).normalize();

            Grenade grenade = new Grenade(world);
            grenade.setOwner(user);
            grenade.setPosition(offsetWeaponPos);
            grenade.setVelocity(destDir.x, destDir.y, destDir.z, GRENADE_PROJECTILE_SPEED, 0);

            if (user.hasStatusEffect(Q3StatusEffects.QUAD_DAMAGE)) grenade.setQuadDamage();
            world.spawnEntity(grenade);

            triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerWorld) world), "controller", "shoot");
        }
    }
}
