package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class PlasmagunPickup extends WeaponPickup {
    public PlasmagunPickup() {
        super();
        this.slot = QuakePlayer.WeaponSlot.PLASMA_GUN;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlasmagunPickupEntity(pos, state);
    }
}
