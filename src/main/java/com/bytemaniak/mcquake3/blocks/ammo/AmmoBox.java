package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.blocks.Pickup;
import com.bytemaniak.mcquake3.blocks.PickupEntity;
import com.bytemaniak.mcquake3.entity.QuakePlayer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AmmoBox extends Pickup {
    protected QuakePlayer.WeaponSlot slot;

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world.isClient) return;

        PickupEntity ammoBox = (PickupEntity)world.getBlockEntity(pos);
        if (entity instanceof PlayerEntity player) {
            QuakePlayer qPlayer = (QuakePlayer)player;
            // TODO: Reimplement giving player ammo
            /* if (qPlayer.getAmmo(slot) < 200 && ammoBox.use()) {
                qPlayer.addAmmo(slot.ammoCount, slot);
                world.markDirty(pos);
            } */
        }
    }
}
