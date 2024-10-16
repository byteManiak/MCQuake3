package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.util.MiscUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
                    int countLeft = Weapon.MAX_AMMO - MiscUtils.getCountOfItemType(player.getInventory(), weapon.ammoType);
                    int count = Math.min(weapon.ammoBoxCount, countLeft);
                    ItemStack ammo = new ItemStack(weapon.ammoType, count);
                    MiscUtils.insertInNonHotbarInventory(ammo, player.getInventory());
                    world.markDirty(pos);
                }
            }
        }
    }
}
