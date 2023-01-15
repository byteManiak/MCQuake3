package com.bytemaniak.mcuake.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Spikes extends Block {
    public Spikes() {
        super(FabricBlockSettings.of(Material.STONE).nonOpaque());
    }

    private final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 14, 12, 14);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    private int SPIKES_DAMAGE_MULTIPLIER = 5;
    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (fallDistance > 1)
        {
            entity.damage(new DamageSource("test"), fallDistance * SPIKES_DAMAGE_MULTIPLIER);
        }
    }
}
