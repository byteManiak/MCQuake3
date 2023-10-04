package com.bytemaniak.mcquake3.blocks.shield;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class HeavyEnergyShieldEntity extends PickupEntity {
    public HeavyEnergyShieldEntity(BlockPos pos, BlockState state) {
        super(Blocks.HEAVY_ENERGY_SHIELD_ENTITY, pos, state, Sounds.HEAVY_ENERGY_SHIELD);
    }
}
