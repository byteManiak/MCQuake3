package com.bytemaniak.mcquake3.blocks.powerup;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Q3StatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuadDamage extends Pickup {
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new QuadDamageEntity(pos, state); }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        if (!world.isClient) {
            PickupEntity quadDamagePickup = (PickupEntity) world.getBlockEntity(pos);
            if (!quadDamagePickup.shouldRender()) return;

            if (entity instanceof PlayerEntity player) {
                player.addStatusEffect(Q3StatusEffects.fromEffect(Registries.STATUS_EFFECT.getEntry(Q3StatusEffects.QUAD_DAMAGE)), null);
                quadDamagePickup.use();
                world.markDirty(pos);
            }
        }
    }
}