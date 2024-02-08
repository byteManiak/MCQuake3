package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class PlasmagunPickupEntity extends WeaponPickupEntity {
    public PlasmagunPickupEntity(BlockPos pos, BlockState state) {
        super(Blocks.PLASMAGUN_ENTITY, pos, state);
    }
}
