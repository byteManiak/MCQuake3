package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class WeaponPickupEntity extends PickupEntity {
    public WeaponPickupEntity(BlockEntityType<? extends WeaponPickupEntity> weapon, BlockPos pos, BlockState state) {
        super(weapon, pos, state, Sounds.WEAPON_PICKUP, Sounds.REGEN, 5);
    }
}
