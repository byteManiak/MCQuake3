package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class GrenadePickup extends WeaponPickup {
    public GrenadePickup() {
        super();
        this.weapon = Weapons.GRENADE_LAUNCHER;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GrenadePickupEntity(pos, state);
    }
}
