package com.bytemaniak.mcquake3.blocks.ammo;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AmmoBox extends BlockWithEntity implements BlockEntityProvider {
    protected QuakePlayer.WeaponSlot slot;

    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 10, 13);

    public AmmoBox() {
        super(FabricBlockSettings.of(Material.METAL).noCollision());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world.isClient) return;

        AmmoBoxEntity ammoBox = (AmmoBoxEntity)world.getBlockEntity(pos);
        if (entity instanceof PlayerEntity player) {
            QuakePlayer qPlayer = (QuakePlayer)player;
            if (qPlayer.getAmmo(slot) < 200 && ammoBox.use()) {
                qPlayer.addAmmo(slot.ammoCount, slot);
                world.markDirty(pos);
                world.playSoundFromEntity(null, entity, Sounds.AMMO_PICKUP, SoundCategory.NEUTRAL, 1, 1);
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return AmmoBoxEntity::tick;
    }
}
