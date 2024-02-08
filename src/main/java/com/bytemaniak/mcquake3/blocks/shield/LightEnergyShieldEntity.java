package com.bytemaniak.mcquake3.blocks.shield;

import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class LightEnergyShieldEntity extends EnergyShieldEntity {
    public LightEnergyShieldEntity(BlockPos pos, BlockState state) {
        super(Blocks.LIGHT_ENERGY_SHIELD_ENTITY, pos, state, Sounds.LIGHT_ENERGY_SHIELD);
    }
}
