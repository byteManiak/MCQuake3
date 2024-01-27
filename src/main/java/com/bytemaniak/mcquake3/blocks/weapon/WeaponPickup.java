package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Weapons;
import com.bytemaniak.mcquake3.util.WeaponSlot;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WeaponPickup extends Pickup {
    protected WeaponSlot slot;

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world.isClient) return;

        PickupEntity weaponPickup = (PickupEntity)world.getBlockEntity(pos);
        if (entity instanceof PlayerEntity player && weaponPickup.use()) {
            ItemStack weapon = new ItemStack(slot.toItem());
            if (!player.getInventory().containsAny(t -> t.isOf(slot.toItem()))) player.giveItemStack(weapon);
            else {
                ItemStack ammo = switch (slot) {
                    case MACHINEGUN -> new ItemStack(Weapons.BULLET, slot.ammoCount);
                    case SHOTGUN -> new ItemStack(Weapons.SHELL, slot.ammoCount);
                    case ROCKET_LAUNCHER -> new ItemStack(Weapons.ROCKET, slot.ammoCount);
                    case GRENADE_LAUNCHER -> new ItemStack(Weapons.GRENADE, slot.ammoCount);
                    default -> null;
                };
                player.giveItemStack(ammo);
            }
            world.markDirty(pos);
        }
    }
}
