package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class LightningAmmoBox extends AmmoBox {
    public LightningAmmoBox() {
        super();
        this.slot = QuakePlayer.WeaponSlot.LIGHTNING_GUN;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LightningAmmoBoxEntity(pos, state);
    }
}
