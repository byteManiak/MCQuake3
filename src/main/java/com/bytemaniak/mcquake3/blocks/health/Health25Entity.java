package com.bytemaniak.mcquake3.blocks.health;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class Health25Entity extends PickupEntity {
    public Health25Entity(BlockPos pos, BlockState state) {
        super(Blocks.HEALTH25_ENTITY, pos, state, Sounds.HEALTH25);
    }
}
