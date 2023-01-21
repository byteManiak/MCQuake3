package com.bytemaniak.mcuake.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public abstract class Weapon extends Item {
    private final long refireRate;

    private long startTick = 0;

    protected Weapon(long refireRateInTicks) {
        super(new Item.Settings().maxCount(1));
        this.refireRate = refireRateInTicks;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // By calling this function, the weapon continues to stay used until the player stops pressing the use key
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    @Override
    // Mimick the animation used to shoot bows. It works pretty well with the weapon models made so far
    public UseAction getUseAction(ItemStack stack) { return UseAction.BOW; }

    @Override
    // Player can shoot weapon indefinitely
    public int getMaxUseTime(ItemStack stack) { return 1000000; }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient) {
            long currentTick = world.getTime();
            if (currentTick - startTick >= refireRate) {
                this.onWeaponRefire(world, user, stack);
                startTick = currentTick;
            }
        }
    }

    protected abstract void onWeaponRefire(World world, LivingEntity user, ItemStack stack);
}
