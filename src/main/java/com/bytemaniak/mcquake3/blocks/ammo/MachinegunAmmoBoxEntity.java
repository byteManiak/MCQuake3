package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class MachinegunAmmoBoxEntity extends PickupEntity {
    public MachinegunAmmoBoxEntity(BlockPos pos, BlockState state) {
        super(Blocks.MACHINEGUN_AMMO_BOX_ENTITY, pos, state);
    }
}
