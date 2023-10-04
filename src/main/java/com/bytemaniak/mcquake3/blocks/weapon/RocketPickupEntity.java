package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class RocketPickupEntity extends PickupEntity {
    public RocketPickupEntity(BlockPos pos, BlockState state) {
        super(Blocks.ROCKET_ENTITY, pos, state, Sounds.WEAPON_PICKUP);
    }
}
