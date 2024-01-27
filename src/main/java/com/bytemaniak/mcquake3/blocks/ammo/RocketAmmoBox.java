package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.util.WeaponSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class RocketAmmoBox extends AmmoBox {
    public RocketAmmoBox() {
        super();
        this.slot = WeaponSlot.ROCKET_LAUNCHER;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RocketAmmoBoxEntity(pos, state);
    }
}
