package com.bytemaniak.mcquake3.blocks.health;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.util.MiscUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class Health extends Pickup {
    protected float healthAmount;
    protected boolean healOver100;

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world.isClient) return;

        PickupEntity healthPickup = (PickupEntity) world.getBlockEntity(pos);
        if (!healthPickup.shouldRender()) return;

        if (entity instanceof PlayerEntity player) {
            float health = player.getHealth();
            if (!healOver100 && (int)health == MiscUtils.toMCDamage(100)) return;

            if (healOver100 || health + healthAmount <= MiscUtils.toMCDamage(100)) {
                player.heal(healthAmount);
                healthPickup.use();
            }
            else {
                player.setHealth(MiscUtils.toMCDamage(100));
                healthPickup.use();
            }
            world.markDirty(pos);
        }
    }
}
