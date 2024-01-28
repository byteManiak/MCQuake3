package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.WeaponInfo;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AmmoBox extends Pickup {
    protected WeaponInfo weaponInfo;

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world.isClient) return;

        PickupEntity ammoBox = (PickupEntity)world.getBlockEntity(pos);
        if (entity instanceof PlayerEntity player) {
            if (ammoBox.use()) {
                player.giveItemStack(new ItemStack(weaponInfo.ammoType(), weaponInfo.ammoCount()));
                world.markDirty(pos);
            }
        }
    }
}
