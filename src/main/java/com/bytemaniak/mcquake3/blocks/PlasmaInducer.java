package com.bytemaniak.mcquake3.blocks;

import com.bytemaniak.mcquake3.registry.Blocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlasmaInducer extends AbstractFurnaceBlock {
    public PlasmaInducer() { super(FabricBlockSettings.of(Material.METAL)); }

	@Override
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)blockEntity : null;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return world.isClient ? null: PlasmaInducer.checkType(type, Blocks.PLASMA_INDUCER_BLOCK_ENTITY, PlasmaInducerEntity::tick);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new PlasmaInducerEntity(pos, state); }

	@Override
	protected void openScreen(World world, BlockPos pos, PlayerEntity player) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof NamedScreenHandlerFactory screenFactory)
			player.openHandledScreen(screenFactory);
	}
}