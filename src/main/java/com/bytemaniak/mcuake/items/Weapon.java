package com.bytemaniak.mcuake.items;

import com.bytemaniak.mcuake.entity.MCuakePlayer;
import com.bytemaniak.mcuake.utils.SoundUtils;
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

    public MCuakePlayer.WeaponSlot weaponSlot;

    protected Weapon(MCuakePlayer.WeaponSlot weaponSlot, long refireRateInTicks, SoundEvent firingSound) {
        super(new Item.Settings().maxCount(1));
        this.weaponSlot = weaponSlot;
        this.refireRate = refireRateInTicks;
        this.firingSound = firingSound;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // By calling this function, the weapon continues to stay used until the player stops pressing the use key
        user.setCurrentHand(hand);
        return TypedActionResult.pass(user.getStackInHand(hand));
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
        MCuakePlayer player = (MCuakePlayer) user;
        boolean clientside = world.isClient;

        if (currentTick - player.getWeaponTick(weaponSlot, clientside) >= refireRate) {
            if (!player.useAmmo(weaponSlot)) {
                if (!clientside) {
                    this.onWeaponRefire(world, user, stack);
                } else {
                    this.onWeaponRefireClient(world, user, stack);
                }

                SoundUtils.playSoundAsPlayer(world, user, firingSound);
            }
            player.setWeaponTick(weaponSlot, currentTick, clientside);
        }
    }

    protected void onWeaponRefireClient(World world, LivingEntity user, ItemStack stack) {
    }

    protected abstract void onWeaponRefire(World world, LivingEntity user, ItemStack stack);
}
