package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class PlasmagunAmmoBoxEntity extends PickupEntity {
    public PlasmagunAmmoBoxEntity(BlockPos pos, BlockState state) {
        super(Blocks.PLASMAGUN_AMMO_BOX_ENTITY, pos, state);
    }
}
