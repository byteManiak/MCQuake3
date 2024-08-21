package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.util.MiscUtils;
import com.bytemaniak.mcquake3.util.QuakePlayer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WeaponPickup extends Pickup {
    protected Weapon weapon;

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        if (!world.isClient) {
            PickupEntity weaponPickup = (PickupEntity) world.getBlockEntity(pos);
            if (entity instanceof PlayerEntity player && weaponPickup.use()) {
                if (!player.getInventory().containsAny(t -> t.isOf(weapon))) {
                    if (((QuakePlayer) player).playingQuakeMap())
                        player.getInventory().insertStack(weapon.slot, new ItemStack(weapon));
                    else
                        player.giveItemStack(new ItemStack(weapon));
                }

                if (!player.isCreative()) {
                    // TODO: Limit ammo usage once Quake server mode is implemented
                    ItemStack ammo = new ItemStack(weapon.ammoType, weapon.ammoBoxCount);
                    MiscUtils.insertInNonHotbarInventory(ammo, player.getInventory());
                }

                world.markDirty(pos);
            }
        }
    }
}
