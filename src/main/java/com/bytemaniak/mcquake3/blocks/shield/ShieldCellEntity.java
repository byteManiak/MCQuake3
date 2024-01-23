package com.bytemaniak.mcquake3.blocks.shield;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class ShieldCellEntity extends PickupEntity {
    public ShieldCellEntity(BlockPos pos, BlockState state) {
        super(Blocks.SHIELD_CELL_ENTITY, pos, state, Sounds.SHIELD_CELL, Sounds.REGEN);
    }
}
