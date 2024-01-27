package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.util.WeaponSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class RailgunAmmoBox extends AmmoBox {
    public RailgunAmmoBox() {
        super();
        this.slot = WeaponSlot.RAILGUN;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RailgunAmmoBoxEntity(pos, state);
    }
}
