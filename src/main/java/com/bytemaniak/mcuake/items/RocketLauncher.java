package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class RocketLauncher extends Weapon {
    private static final long ROCKET_REFIRE_RATE = 15;
    private static final float ROCKET_PROJECTILE_SPEED = 1.5f;

    public RocketLauncher() {
        super(QuakePlayer.WeaponSlot.GRENADE_LAUNCHER, new Identifier("mcuake", "rocket_launcher"),
                ROCKET_REFIRE_RATE, true, Sounds.GRENADE_FIRE, false);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack, Vec3d lookDir, Vec3d weaponPos) {
        triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerWorld) world), "controller", "shoot");
    }

    @Override
    protected PlayState handle(AnimationState<Weapon> state) {
        return null;
    }
}
