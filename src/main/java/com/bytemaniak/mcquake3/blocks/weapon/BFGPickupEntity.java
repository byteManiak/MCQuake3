package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BFGPickupEntity extends WeaponPickupEntity {
    public BFGPickupEntity(BlockPos pos, BlockState state) {
        super(Blocks.BFG_ENTITY, pos, state);
    }
}
