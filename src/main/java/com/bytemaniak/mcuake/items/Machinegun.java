package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class Machinegun extends HitscanWeapon {
    private static final long MACHINEGUN_REFIRE_TICK_RATE = 2;
    private static final int MACHINEGUN_QUAKE_DAMAGE = 7;
    private static final int MACHINEGUN_MC_DAMAGE = 2;
    private static final float MACHINEGUN_RANGE = 200;

    public Machinegun() {
        super(QuakePlayer.WeaponSlot.MACHINEGUN, new Identifier("mcuake", "machinegun"),
                MACHINEGUN_REFIRE_TICK_RATE, true, Sounds.MACHINEGUN_FIRE, false,
                MACHINEGUN_QUAKE_DAMAGE, MACHINEGUN_MC_DAMAGE, DamageSources.MACHINEGUN_DAMAGE, MACHINEGUN_RANGE);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        stack.getOrCreateNbt().putDouble("firing_speed", 1.0);
        super.onWeaponRefire(world, user, stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            double speed = stack.getOrCreateNbt().getDouble("firing_speed");
            speed -= 0.03;
            if (speed < 0.4) {
                speed = 0.0;
            }
            stack.getNbt().putDouble("firing_speed", speed);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    protected PlayState handle(AnimationState<Weapon> state) {
        state.getController().setAnimation(DefaultAnimations.ATTACK_SHOOT);
        ItemStack stack = state.getData(DataTickets.ITEMSTACK);

        double speed = 0.0;
        if (stack.getNbt() != null)
            speed = stack.getNbt().getDouble("firing_speed");

        state.getController().setAnimationSpeed(speed);
        return PlayState.CONTINUE;
    }
}
