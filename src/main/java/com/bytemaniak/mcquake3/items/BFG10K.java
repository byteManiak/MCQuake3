package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.entity.projectile.BFG10KProjectile;
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

public class BFG10K extends Weapon {
    private static final long BFG_REFIRE_RATE_Q3 = 4;
    private static final long BFG_REFIRE_RATE_QL = 7;

    private static final float BFG_PROJECTILE_SPEED = 1.5f;

    public BFG10K() {
        super(new Identifier("mcquake3:bfg10k"), BFG_REFIRE_RATE_Q3, BFG_REFIRE_RATE_QL,
                true, Sounds.BFG10K_FIRE, false, Weapons.BFG10K_ROUND, 20, 15, 8);
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

            BFG10KProjectile bfg10KProjectile = new BFG10KProjectile(world);
            bfg10KProjectile.setOwner(user);
            bfg10KProjectile.setPosition(offsetWeaponPos);
            bfg10KProjectile.setVelocity(destDir.x, destDir.y, destDir.z, BFG_PROJECTILE_SPEED, 0);

            if (user.hasStatusEffect(Q3StatusEffects.QUAD_DAMAGE)) bfg10KProjectile.setQuadDamage();
            world.spawnEntity(bfg10KProjectile);

            triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerWorld) world), "controller", "shoot");
        }
    }
}
