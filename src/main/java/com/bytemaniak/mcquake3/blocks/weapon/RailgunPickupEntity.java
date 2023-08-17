package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class RailgunPickupEntity extends PickupEntity {
    public RailgunPickupEntity(BlockPos pos, BlockState state) {
        super(Blocks.RAILGUN_ENTITY, pos, state, Sounds.AMMO_PICKUP);
    }
}