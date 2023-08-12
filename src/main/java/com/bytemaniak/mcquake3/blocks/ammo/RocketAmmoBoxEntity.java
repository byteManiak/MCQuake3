package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class RocketAmmoBoxEntity extends AmmoBoxEntity {
    public RocketAmmoBoxEntity(BlockPos pos, BlockState state) {
        super(Blocks.ROCKET_AMMO_BOX_ENTITY, pos, state);
    }
}
