package com.bytemaniak.mcquake3.blocks.powerup;

import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class InvisibilityEntity extends PowerupEntity {
    public InvisibilityEntity(BlockPos pos, BlockState state) {
        super(Blocks.INVISIBILITY_ENTITY, pos, state, Sounds.INVISIBILITY);
    }
}
