package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class ShotgunAmmoBoxEntity extends AmmoBoxEntity {
    public ShotgunAmmoBoxEntity(BlockPos pos, BlockState state) {
        super(Blocks.SHOTGUN_AMMO_BOX_ENTITY, pos, state);
    }
}
