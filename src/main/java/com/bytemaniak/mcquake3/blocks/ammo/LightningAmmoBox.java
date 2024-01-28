package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.registry.WeaponInfo;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class LightningAmmoBox extends AmmoBox {
    public LightningAmmoBox() {
        super();
        this.weaponInfo = WeaponInfo.LIGHTNING_GUN;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LightningAmmoBoxEntity(pos, state);
    }
}
