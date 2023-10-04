package com.bytemaniak.mcquake3.blocks.armor;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class LightArmor extends Armor {
    public LightArmor() {
        super();
        this.armorValue = 50;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new LightArmorEntity(pos, state); }
}
