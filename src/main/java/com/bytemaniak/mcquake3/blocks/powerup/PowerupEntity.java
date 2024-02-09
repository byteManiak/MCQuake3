package com.bytemaniak.mcquake3.blocks.powerup;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class PowerupEntity extends PickupEntity {
    public PowerupEntity(BlockEntityType<? extends PowerupEntity> powerup, BlockPos pos, BlockState state, SoundEvent sound) {
        super(powerup, pos, state, sound, Sounds.POWERUP_REGEN, 60);
    }
}
