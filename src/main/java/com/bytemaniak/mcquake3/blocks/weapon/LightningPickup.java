package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class LightningPickup extends WeaponPickup {
    public LightningPickup() {
        super();
        this.weapon = Weapons.LIGHTNING_GUN;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LightningPickupEntity(pos, state);
    }
}
