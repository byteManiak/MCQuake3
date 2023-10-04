package com.bytemaniak.mcquake3.blocks.armor;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ArmorShard extends Armor {
    public ArmorShard() {
        super();
        this.armorValue = 5;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new ArmorShardEntity(pos, state); }
}
