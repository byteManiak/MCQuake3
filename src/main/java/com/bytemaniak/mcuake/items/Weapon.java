package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.utils.ClientUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public abstract class Weapon extends Item {
    private final long refireRate;
    private final SoundEvent firingSound;

    private long startTick = 0;
    private long clientStartTick = 0;

    protected Weapon(long refireRateInTicks, SoundEvent firingSound) {
        super(new Item.Settings().maxCount(1));
        this.refireRate = refireRateInTicks;
        this.firingSound = firingSound;
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
        long currentTick = world.getTime();
        if (!world.isClient) {
            if (currentTick - startTick >= refireRate) {
                this.onWeaponRefire(world, user, stack);
                world.playSound(user, user.getBlockPos(), firingSound, SoundCategory.PLAYERS, .65f, 1.f);
                startTick = currentTick;
            }
        } else {
            if (currentTick - clientStartTick >= refireRate) {
                ClientUtils.playSoundPositioned(user, firingSound);
                clientStartTick = currentTick;
            }
        }
    }

    protected abstract void onWeaponRefire(World world, LivingEntity user, ItemStack stack);
}
