package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.util.WeaponSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class PlasmagunAmmoBox extends AmmoBox {
    public PlasmagunAmmoBox() {
        super();
        this.slot = WeaponSlot.PLASMA_GUN;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlasmagunAmmoBoxEntity(pos, state);
    }
}
