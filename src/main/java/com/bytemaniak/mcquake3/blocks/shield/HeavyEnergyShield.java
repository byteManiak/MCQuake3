package com.bytemaniak.mcquake3.blocks.shield;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class HeavyEnergyShield extends EnergyShield {
    public HeavyEnergyShield() {
        super();
        this.armorValue = 100;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new HeavyEnergyShieldEntity(pos, state);
    }
}
