package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class MachinegunAmmoBox extends AmmoBox {
    public MachinegunAmmoBox() {
        super();
        this.weapon = Weapons.MACHINEGUN;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MachinegunAmmoBoxEntity(pos, state);
    }
}
