package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class RocketPickup extends WeaponPickup {
    public RocketPickup() {
        super();
        this.slot = QuakePlayer.WeaponSlot.ROCKET_LAUNCHER;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RocketPickupEntity(pos, state);
    }
}