package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.util.WeaponSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class MachinegunPickup extends WeaponPickup {
    public MachinegunPickup() {
        super();
        this.slot = WeaponSlot.MACHINEGUN;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MachinegunPickupEntity(pos, state);
    }
}
