package com.bytemaniak.mcquake3.blocks.health;

import com.bytemaniak.mcquake3.util.MiscUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class Health50 extends Health {
    public Health50() {
        super();
        this.healthAmount = MiscUtils.toMCDamage(50);
        this.healOver100 = false;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new Health50Entity(pos, state);
    }
}
