package com.bytemaniak.mcuake.blocks;

import com.bytemaniak.mcuake.MCuake;
import com.bytemaniak.mcuake.registry.DamageSources;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Spikes extends Block {
    private static final int SPIKES_DAMAGE_MULTIPLIER = 5;
    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 12, 15);

    public Spikes() {
        super(FabricBlockSettings.of(Material.STONE).nonOpaque());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity instanceof LivingEntity && fallDistance > 0.75) {
            entity.damage(DamageSources.SPIKES, fallDistance * SPIKES_DAMAGE_MULTIPLIER);
        }
    }
}
