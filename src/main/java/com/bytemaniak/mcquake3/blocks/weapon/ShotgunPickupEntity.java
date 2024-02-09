package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class ShotgunPickupEntity extends WeaponPickupEntity {
    public ShotgunPickupEntity(BlockPos pos, BlockState state) {
        super(Blocks.SHOTGUN_ENTITY, pos, state);
    }
}
