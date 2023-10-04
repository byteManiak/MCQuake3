package com.bytemaniak.mcquake3.blocks.armor;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class HeavyArmorEntity extends PickupEntity {
    public HeavyArmorEntity(BlockPos pos, BlockState state) {
        super(Blocks.HEAVY_ARMOR_ENTITY, pos, state, Sounds.HEAVY_ARMOR);
    }
}
