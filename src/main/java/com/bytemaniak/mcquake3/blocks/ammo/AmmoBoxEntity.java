package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class AmmoBoxEntity extends PickupEntity {
    public AmmoBoxEntity(BlockEntityType<? extends AmmoBoxEntity> ammo, BlockPos pos, BlockState state) {
        super(ammo, pos, state, Sounds.AMMO_PICKUP, Sounds.REGEN, 5);
    }
}
