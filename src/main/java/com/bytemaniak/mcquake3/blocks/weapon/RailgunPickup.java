package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class RailgunPickup extends WeaponPickup {
    public RailgunPickup() {
        super();
        this.weapon = Weapons.RAILGUN;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RailgunPickupEntity(pos, state);
    }
}
