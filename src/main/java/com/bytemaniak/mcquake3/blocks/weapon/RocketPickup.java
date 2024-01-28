package com.bytemaniak.mcquake3.blocks.weapon;

import com.bytemaniak.mcquake3.registry.WeaponInfo;
import com.bytemaniak.mcquake3.registry.Weapons;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class RocketPickup extends WeaponPickup {
    public RocketPickup() {
        super();
        this.weapon = Weapons.ROCKET_LAUNCHER;
        this.weaponInfo = WeaponInfo.ROCKET_LAUNCHER;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RocketPickupEntity(pos, state);
    }
}
