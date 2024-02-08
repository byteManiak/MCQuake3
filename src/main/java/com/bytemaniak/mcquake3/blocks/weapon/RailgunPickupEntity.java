package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class RailgunPickupEntity extends WeaponPickupEntity {
    public RailgunPickupEntity(BlockPos pos, BlockState state) {
        super(Blocks.RAILGUN_ENTITY, pos, state);
    }
}
