package com.bytemaniak.mcquake3.items;

import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.util.MiscUtils;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animation.PlayState;

public class Machinegun extends HitscanWeapon {
    private static final long MACHINEGUN_REFIRE_RATE = 2;
    private static final float MACHINEGUN_DAMAGE = MiscUtils.toMCDamage(7);
    private static final float MACHINEGUN_RANGE = 200;

    public Machinegun() {
        super(Identifier.of("mcquake3:machinegun"), MACHINEGUN_REFIRE_RATE, MACHINEGUN_REFIRE_RATE,
                true, Sounds.MACHINEGUN_FIRE, false, MACHINEGUN_DAMAGE, Q3DamageSources.MACHINEGUN_DAMAGE,
                MACHINEGUN_RANGE, Weapons.BULLET, 100, 50, 1, false);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack, Vec3d lookDir, Vec3d weaponPos) {
        if (!world.isClient) {
            ///stack.getOrCreateNbt().putDouble("firing_speed", 1.0);
            setAnimData(user, GeoItem.getOrAssignId(stack, (ServerWorld) world), SPEED, 1.0);
        }
        super.onWeaponRefire(world, user, stack, lookDir, weaponPos);
    }

    @Override
    protected void onProjectileCollision(World world, LivingEntity user, Vec3d userPos, Vec3d iterPos, Vec3d upVec, boolean isBlockCollision) {
        if (!world.isClient) {
            if (isBlockCollision)
                world.playSound(null, new BlockPos((int) iterPos.x, (int) iterPos.y, (int) iterPos.z), Sounds.BULLET_MISS, SoundCategory.NEUTRAL, .75f, 1);
            ((ServerWorld)world).spawnParticles(ParticleTypes.LAVA, iterPos.x, iterPos.y, iterPos.z, 1, Math.random() / 5, 0.1, Math.random() / 5, 1);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            double speed = 0;
            long id = GeoItem.getOrAssignId(stack, (ServerWorld) world);
            try {
                ///speed = stack.getOrCreateNbt().getDouble("firing_speed");
            } catch (NullPointerException e) {
                speed = 0.0;
            }

            speed -= 0.03;
            if (speed < 0.4) speed = 0.0;

            ///stack.getOrCreateNbt().putDouble("firing_speed", speed);
            setAnimData(entity, id, SPEED, speed);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    protected PlayState handle(AnimationState state) {
        ///state.getController().setAnimation(DefaultAnimations.ATTACK_SHOOT);
        ///ItemStack stack = state.getData(DataTickets.ITEMSTACK);

        double speed;
        try {
            ///speed = getAnimData(GeoItem.getId(stack), SPEED);
        } catch (NullPointerException e) {
            speed = 0.0;
        }

        ///state.getController().setAnimationSpeed(speed);
        return PlayState.CONTINUE;
    }

    @Override
    public PlayState handle(software.bernie.geckolib.animation.AnimationState<Weapon> state) {
        return null;
    }
}
