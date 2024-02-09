package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class LightningPickupEntity extends WeaponPickupEntity {
    public LightningPickupEntity(BlockPos pos, BlockState state) {
        super(Blocks.LIGHTNING_ENTITY, pos, state);
    }
}
