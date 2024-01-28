package com.bytemaniak.mcquake3.blocks.powerup;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.registry.Q3StatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Invisibility extends Pickup {
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new InvisibilityEntity(pos, state); }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);

        if (!world.isClient) {
            PickupEntity invisibilityPickup = (PickupEntity) world.getBlockEntity(pos);
            if (!invisibilityPickup.shouldRender()) return;

            if (entity instanceof PlayerEntity player) {
                player.addStatusEffect(Q3StatusEffects.fromEffect(StatusEffects.INVISIBILITY), null);
                invisibilityPickup.use();
                world.markDirty(pos);
            }
        }
    }
}
