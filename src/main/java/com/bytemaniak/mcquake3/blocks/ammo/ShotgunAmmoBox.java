package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.util.WeaponSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class ShotgunAmmoBox extends AmmoBox {
    public ShotgunAmmoBox() {
        super();
        this.slot = WeaponSlot.SHOTGUN;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShotgunAmmoBoxEntity(pos, state);
    }
}
