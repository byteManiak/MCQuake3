package com.bytemaniak.mcquake3.blocks.shield;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ShieldCell extends EnergyShield {
    public ShieldCell() {
        super();
        this.armorValue = 5;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new ShieldCellEntity(pos, state); }
}
