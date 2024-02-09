package com.bytemaniak.mcquake3.blocks.shield;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class EnergyShieldEntity extends PickupEntity {
    public EnergyShieldEntity(BlockEntityType<? extends EnergyShieldEntity> shield, BlockPos pos, BlockState state, SoundEvent sound) {
        super(shield, pos, state, sound, Sounds.REGEN, 30);
    }
}
