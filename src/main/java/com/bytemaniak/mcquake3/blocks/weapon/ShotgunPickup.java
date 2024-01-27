package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.util.WeaponSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class ShotgunPickup extends WeaponPickup {
    public ShotgunPickup() {
        super();
        this.slot = WeaponSlot.SHOTGUN;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShotgunPickupEntity(pos, state);
    }
}
