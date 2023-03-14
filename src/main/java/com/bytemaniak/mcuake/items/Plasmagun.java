package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.entity.projectile.PlasmaBall;
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

public class Plasmagun extends Weapon {
    private static final long PLASMAGUN_REFIRE_TICK_RATE = 2;

    private static final float PLASMAGUN_RANGE = 75;
    private static final float PLASMAGUN_VERTICAL_SPAWN_OFFSET = .6f;
    private static final float PLASMAGUN_HORIZONTAL_SPAWN_OFFSET = -.1f;
    private static final float PLASMAGUN_FORWARD_SPAWN_OFFSET = .5f;
    private static final float PLASMAGUN_PROJECTILE_SPEED = 1.5f;

    public Plasmagun() {
        super(QuakePlayer.WeaponSlot.PLASMA_GUN, new Identifier("mcuake", "plasmagun"),
                PLASMAGUN_REFIRE_TICK_RATE, true, Sounds.PLASMAGUN_FIRE, false);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack, Vec3d lookDir, Vec3d weaponPos) {
        // Spawn a new plasma ball approximately from the weapon
        Vec3d upVec = Vec3d.fromPolar(user.getPitch() + 90, user.getYaw()).normalize();
        Vec3d rightVec = lookDir.crossProduct(upVec).normalize();
        Vec3d offsetWeaponPos = weaponPos
                .add(upVec.multiply(PLASMAGUN_VERTICAL_SPAWN_OFFSET))
                .add(rightVec.multiply(PLASMAGUN_HORIZONTAL_SPAWN_OFFSET))
                .add(lookDir.multiply(PLASMAGUN_FORWARD_SPAWN_OFFSET));

        // The furthest point, to which the projectile will go towards
        Vec3d destPos = user.getEyePos().add(lookDir.multiply(PLASMAGUN_RANGE));
        Vec3d destDir = destPos.subtract(offsetWeaponPos).normalize();

        PlasmaBall plasmaBall = new PlasmaBall(world);
        plasmaBall.setOwner(user);
        plasmaBall.setPosition(offsetWeaponPos);
        plasmaBall.setVelocity(destDir.x, destDir.y, destDir.z, PLASMAGUN_PROJECTILE_SPEED, 0);
        world.spawnEntity(plasmaBall);

        triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerWorld) world), "controller", "shoot");
    }

    @Override
    protected PlayState handle(AnimationState<Weapon> state) {
        return null;
    }
}
