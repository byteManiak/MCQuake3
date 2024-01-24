package com.bytemaniak.mcquake3.blocks.powerup;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class QuadDamageEntity extends PickupEntity {
    public QuadDamageEntity(BlockPos pos, BlockState state) {
        super(Blocks.QUAD_DAMAGE_ENTITY, pos, state, Sounds.QUAD_DAMAGE, Sounds.POWERUP_REGEN, 60);
    }
}
