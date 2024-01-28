package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.WeaponInfo;
import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BFGPickup extends WeaponPickup {
    public BFGPickup() {
        super();
        this.weapon = Weapons.BFG10K;
        this.weaponInfo = WeaponInfo.BFG10K;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BFGPickupEntity(pos, state);
    }
}
