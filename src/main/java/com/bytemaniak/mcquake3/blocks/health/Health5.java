package com.bytemaniak.mcquake3.blocks.health;

import com.bytemaniak.mcquake3.util.MiscUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class Health5 extends Health {
    public Health5() {
        super();
        this.healthAmount = MiscUtils.toMCDamage(5);
        this.healOver100 = true;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new Health5Entity(pos, state);
    }
}
