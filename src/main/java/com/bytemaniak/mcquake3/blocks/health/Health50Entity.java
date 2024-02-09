package com.bytemaniak.mcquake3.blocks.health;

import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class Health50Entity extends HealthEntity {
    public Health50Entity(BlockPos pos, BlockState state) {
        super(Blocks.HEALTH50_ENTITY, pos, state, Sounds.HEALTH50);
    }
}
