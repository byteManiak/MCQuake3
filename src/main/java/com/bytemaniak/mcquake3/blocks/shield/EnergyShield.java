package com.bytemaniak.mcquake3.blocks.shield;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class EnergyShield extends Pickup {
    protected float armorValue;

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world.isClient) return;

        PickupEntity armorPickup = (PickupEntity) world.getBlockEntity(pos);
        if (!armorPickup.shouldRender()) return;

        if (entity instanceof PlayerEntity player) {
            armorPickup.use();
            world.markDirty(pos);
        }
    }
}
