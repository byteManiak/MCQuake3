package com.bytemaniak.mcquake3.blocks.powerup;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class HasteEntity extends PickupEntity {
    public HasteEntity(BlockPos pos, BlockState state) { super(Blocks.HASTE_ENTITY, pos, state, Sounds.AMMO_PICKUP); }
}
