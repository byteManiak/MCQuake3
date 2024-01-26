package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.entity.QuakePlayer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WeaponPickup extends Pickup {
    protected QuakePlayer.WeaponSlot slot;

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world.isClient) return;

        PickupEntity weaponPickup = (PickupEntity)world.getBlockEntity(pos);
        if (entity instanceof PlayerEntity player && weaponPickup.use()) {
            QuakePlayer qPlayer = (QuakePlayer)player;
            ItemStack weapon = new ItemStack(slot.toItem());
            if (!player.getInventory().containsAny(t -> t.isOf(slot.toItem()))) player.giveItemStack(weapon);
            // TODO: Reimplement giving player some ammo when picking up weapon
            // else qPlayer.addAmmo(slot.ammoCount, slot);

            world.markDirty(pos);
        }
    }
}
