package com.bytemaniak.mcquake3.blocks.shield;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class LightEnergyShield extends EnergyShield {
    public LightEnergyShield() {
        super();
        this.armorValue = 50;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new LightEnergyShieldEntity(pos, state); }
}
