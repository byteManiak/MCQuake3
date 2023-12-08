package com.bytemaniak.mcquake3.blocks.powerup;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class QuadDamage extends Pickup {
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new QuadDamageEntity(pos, state); }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world.isClient) return;

        PickupEntity quadDamagePickup = (PickupEntity) world.getBlockEntity(pos);
        if (!quadDamagePickup.shouldRender()) return;

        if (entity instanceof PlayerEntity player) {
            //player.addStatusEffect(Q3StatusEffects.INVISIBILITY_STATUS_EFFECT, null);
            quadDamagePickup.use();
            world.markDirty(pos);
        }
    }
}