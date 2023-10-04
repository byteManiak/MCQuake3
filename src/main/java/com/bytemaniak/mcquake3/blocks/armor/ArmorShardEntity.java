package com.bytemaniak.mcquake3.blocks.armor;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class ArmorShardEntity extends PickupEntity {
    public ArmorShardEntity(BlockPos pos, BlockState state) {
        super(Blocks.ARMOR_SHARD_ENTITY, pos, state, Sounds.ARMOR_SHARD);
    }
}
