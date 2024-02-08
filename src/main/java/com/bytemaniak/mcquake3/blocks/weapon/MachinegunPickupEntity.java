package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class MachinegunPickupEntity extends WeaponPickupEntity {
    public MachinegunPickupEntity(BlockPos pos, BlockState state) {
        super(Blocks.MACHINEGUN_ENTITY, pos, state);
    }
}
