package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BFGPickupEntity extends PickupEntity {
    public BFGPickupEntity(BlockPos pos, BlockState state) {
        super(Blocks.BFG_ENTITY, pos, state, Sounds.WEAPON_PICKUP, Sounds.REGEN, 5);
    }
}
