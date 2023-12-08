package com.bytemaniak.mcquake3.blocks.powerup;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Q3StatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Haste extends Pickup {
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new HasteEntity(pos, state); }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world.isClient) return;

        PickupEntity hastePickup = (PickupEntity) world.getBlockEntity(pos);
        if (!hastePickup.shouldRender()) return;

        if (entity instanceof PlayerEntity player) {
            player.addStatusEffect(Q3StatusEffects.SPEED_STATUS_EFFECT, null);
            player.addStatusEffect(Q3StatusEffects.HASTE_STATUS_EFFECT, null);
            hastePickup.use();
            world.markDirty(pos);
        }
    }
}
