package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.items.Weapon;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AmmoBox extends Pickup {
    protected Weapon weapon;

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        if (!world.isClient) {
            PickupEntity ammoBox = (PickupEntity) world.getBlockEntity(pos);
            if (entity instanceof PlayerEntity player && !player.isCreative()) {
                if (ammoBox.use()) {
                    // TODO: Limit ammo usage once Quake server mode is implemented
                    PlayerInventory inventory = player.getInventory();
                    DefaultedList<ItemStack> main = inventory.main;
                    ItemStack ammo = new ItemStack(weapon.ammoType, weapon.ammoBoxCount);

                    // Prioritize the non-hotbar inventory for ammo pickup
                    for (int i = 9; i < main.size(); ++i) {
                        if (ammo.getCount() == 0) break;

                        if (main.get(i).isOf(weapon.ammoType)) {
                            ammo.setCount(inventory.addStack(i, ammo));
                        }
                        else if (main.get(i).isEmpty()) {
                            player.getInventory().insertStack(i, ammo);
                            break;
                        }
                    }

                    player.getInventory().insertStack(ammo);

                    world.markDirty(pos);
                }
            }
        }
    }
}
