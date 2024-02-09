package com.bytemaniak.mcquake3.blocks.health;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class HealthEntity extends PickupEntity {
    public HealthEntity(BlockEntityType<? extends HealthEntity> health, BlockPos pos, BlockState state, SoundEvent sound) {
        super(health, pos, state, sound, Sounds.REGEN, 35);
    }
}
