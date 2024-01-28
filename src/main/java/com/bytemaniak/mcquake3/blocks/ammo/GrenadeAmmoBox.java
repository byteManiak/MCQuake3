package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class GrenadeAmmoBox extends AmmoBox {
    public GrenadeAmmoBox() {
        super();
        this.weapon = Weapons.GRENADE_LAUNCHER;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GrenadeAmmoBoxEntity(pos, state);
    }
}
