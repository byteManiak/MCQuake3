package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.util.WeaponSlot;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class LightningPickup extends WeaponPickup {
    public LightningPickup() {
        super();
        this.slot = WeaponSlot.LIGHTNING_GUN;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LightningPickupEntity(pos, state);
    }
}
