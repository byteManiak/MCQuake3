package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class RailgunAmmoBoxEntity extends AmmoBoxEntity {
    public RailgunAmmoBoxEntity(BlockPos pos, BlockState state) {
        super(Blocks.RAILGUN_AMMO_BOX_ENTITY, pos, state);
    }
}
