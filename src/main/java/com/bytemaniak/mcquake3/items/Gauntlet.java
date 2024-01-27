package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.util.MiscUtils;
import com.bytemaniak.mcquake3.util.WeaponSlot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class Gauntlet extends HitscanWeapon {
    private static final long GAUNTLET_REFIRE_RATE = 10;
    private static final float GAUNTLET_DAMAGE = MiscUtils.toMCDamage(50);
    private static final float GAUNTLET_RANGE = 2.5f;
    private static final float GAUNTLET_HITSCAN_STEP = 0.15f;

    public Gauntlet() {
        super(WeaponSlot.GAUNTLET, new Identifier("mcquake3:gauntlet"),
                GAUNTLET_REFIRE_RATE, false, null, true,
                GAUNTLET_DAMAGE, Q3DamageSources.GAUNTLET_DAMAGE,
                GAUNTLET_RANGE, GAUNTLET_HITSCAN_STEP);
    }

    @Override
    protected void onDamage(World world, LivingEntity attacked) {
        world.playSoundFromEntity(null, attacked, Sounds.GAUNTLET_DAMAGE, SoundCategory.PLAYERS, 1, 1);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack, Vec3d lookDir, Vec3d weaponPos) {
        if (!world.isClient) {
            stack.getOrCreateNbt().putDouble("firing_speed", 3.0);
            setAnimData(user, GeoItem.getOrAssignId(stack, (ServerWorld) world), SPEED, 3.0);
        }
        super.onWeaponRefire(world, user, stack, lookDir, weaponPos);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            double speed;
            long id = GeoItem.getOrAssignId(stack, (ServerWorld) world);
            try {
                speed = stack.getOrCreateNbt().getDouble("firing_speed");
            } catch (NullPointerException e) {
                speed = 0.0;
            }

            speed -= 0.1;
            if (speed < 0.1) {
                speed = 0.1;
            }

            stack.getOrCreateNbt().putDouble("firing_speed", speed);
            setAnimData(entity, id, SPEED, speed);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    protected PlayState handle(AnimationState<Weapon> state) {
        state.getController().setAnimation(DefaultAnimations.ATTACK_SHOOT);
        ItemStack stack = state.getData(DataTickets.ITEMSTACK);

        double speed;
        try {
            speed = getAnimData(GeoItem.getId(stack), SPEED);
        } catch (NullPointerException e) {
            speed = 0.1;
        }

        state.getController().setAnimationSpeed(speed);
        return PlayState.CONTINUE;
    }
}
