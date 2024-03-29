package com.bytemaniak.mcquake3.blocks;

import com.bytemaniak.mcquake3.registry.Q3DamageSources;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class Spikes extends Block {
    private static final int SPIKES_DAMAGE_MULTIPLIER = 5;
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 12, 16);

    public Spikes() {
        super(FabricBlockSettings.of(Material.STONE).nonOpaque());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (!world.isClient) {
            if (entity instanceof LivingEntity && entity.fallDistance > 0.75) {
                DamageSource spikeDamage = Q3DamageSources.of(world, Q3DamageSources.SPIKES, null, null);
                entity.damage(spikeDamage, entity.fallDistance * SPIKES_DAMAGE_MULTIPLIER);
            }
        }
    }
}
