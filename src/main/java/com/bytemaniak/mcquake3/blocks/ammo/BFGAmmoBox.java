package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class BFGAmmoBox extends AmmoBox {
    public BFGAmmoBox() {
        super();
        this.slot = QuakePlayer.WeaponSlot.BFG10K;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BFGAmmoBoxEntity(pos, state);
    }
}
