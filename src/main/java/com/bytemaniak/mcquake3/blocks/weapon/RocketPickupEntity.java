package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class RocketPickupEntity extends WeaponPickupEntity {
    public RocketPickupEntity(BlockPos pos, BlockState state) {
        super(Blocks.ROCKET_ENTITY, pos, state);
    }
}
