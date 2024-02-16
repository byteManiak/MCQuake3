package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.registry.client.Renderers;
import com.bytemaniak.mcquake3.util.MiscUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;

public class Railgun extends HitscanWeapon {
    private static final int RAILGUN_REFIRE_TICK_RATE = 50;
    private static final float RAILGUN_DAMAGE = MiscUtils.toMCDamage(100);
    private static final float RAILGUN_RANGE = 200;

    public Railgun() {
        super(new Identifier("mcquake3:railgun"), RAILGUN_REFIRE_TICK_RATE, true, Sounds.RAILGUN_FIRE, false,
                RAILGUN_DAMAGE, Q3DamageSources.RAILGUN_DAMAGE, RAILGUN_RANGE, Weapons.RAILGUN_ROUND, 10, 10, 6);
    }

    @Override
    protected void onProjectileCollision(World world, LivingEntity user, Vec3d userPos, Vec3d iterPos, Vec3d upVec, boolean isBlockCollision) {
        if (world.isClient) {
            if (isBlockCollision) Renderers.feedbacks.lastHitRailgun = false;
            submitRailgunTrail(user, userPos, iterPos, upVec);
        }
    }

    @Environment(EnvType.CLIENT)
    private void submitRailgunTrail(LivingEntity user, Vec3d startPos, Vec3d endPos, Vec3d upVec) {
        Renderers.trailRenderer.addTrail(startPos, endPos, upVec, user.getUuid(), slot);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack, Vec3d lookDir, Vec3d weaponPos) {
        if (!world.isClient)
            triggerAnim(user, GeoItem.getOrAssignId(stack, (ServerWorld) world), "controller", "shoot");

        super.onWeaponRefire(world, user, stack, lookDir, weaponPos);
    }
}
