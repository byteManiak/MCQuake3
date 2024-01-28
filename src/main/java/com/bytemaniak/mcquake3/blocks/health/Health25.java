package com.bytemaniak.mcquake3.blocks.health;

import com.bytemaniak.mcquake3.util.MiscUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class Health25 extends Health {
    public Health25() {
        super();
        this.healthAmount = MiscUtils.toMCDamage(25);
        this.healOver100 = false;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new Health25Entity(pos, state);
    }
}
