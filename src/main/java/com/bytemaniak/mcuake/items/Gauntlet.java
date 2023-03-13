package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.QuakePlayer;
import com.bytemaniak.mcuake.registry.DamageSources;
import com.bytemaniak.mcuake.registry.Sounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class Gauntlet extends HitscanWeapon {
    private static final long GAUNTLET_REFIRE_RATE = 10;
    private static final int GAUNTLET_QUAKE_DAMAGE = 50;
    private static final int GAUNTLET_MC_DAMAGE = 5;
    private static final float GAUNTLET_RANGE = 2.5f;
    private static final float GAUNTLET_HITSCAN_STEP = 0.15f;

    public Gauntlet() {
        super(QuakePlayer.WeaponSlot.GAUNTLET, new Identifier("mcuake", "gauntlet"),
                GAUNTLET_REFIRE_RATE, false, null, true,
                GAUNTLET_QUAKE_DAMAGE, GAUNTLET_MC_DAMAGE, DamageSources.GAUNTLET_DAMAGE,
                GAUNTLET_RANGE, GAUNTLET_HITSCAN_STEP);
    }

    @Override
    protected void onQuakeDamage(World world, LivingEntity attacked) {
        world.playSoundFromEntity(null, attacked, Sounds.GAUNTLET_DAMAGE, SoundCategory.PLAYERS, 1, 1);
    }

    @Override
    protected void onMcDamage(World world, LivingEntity attacked) {
        onQuakeDamage(world, attacked);
    }

    @Override
    protected void onWeaponRefire(World world, LivingEntity user, ItemStack stack) {
        stack.getOrCreateNbt().putDouble("firing_speed", 3.0);
        super.onWeaponRefire(world, user, stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient) {
            double speed = stack.getOrCreateNbt().getDouble("firing_speed");
            speed -= 0.1;
            if (speed < 0.1) {
                speed = 0.1;
            }
            stack.getNbt().putDouble("firing_speed", speed);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    protected PlayState handle(AnimationState<Weapon> state) {
        state.getController().setAnimation(DefaultAnimations.ATTACK_SHOOT);
        ItemStack stack = state.getData(DataTickets.ITEMSTACK);

        double speed = 0.1;
        if (stack.getNbt() != null)
            speed = stack.getNbt().getDouble("firing_speed");

        state.getController().setAnimationSpeed(speed);
        return PlayState.CONTINUE;
    }
}
