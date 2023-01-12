package com.bytemaniak.mcuake.blocks;

import com.bytemaniak.mcuake.MCuake;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class MCuakeJumppad extends HorizontalFacingBlock {
	private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public MCuakeJumppad() {
		super(FabricBlockSettings.of(Material.METAL));
	}

	private static VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 4, 16);

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context)
	{
		return SHAPE;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context)
	{
		return this.getDefaultState().with(FACING, context.getPlayerFacing());
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation)
	{
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	private static final float JUMPPAD_BOOST_VALUE = 3.f;
	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity)
	{
		if (entity instanceof LivingEntity)
		{
			Direction direction = state.get(FACING);
			entity.addVelocity(Vec3d.of(direction.getVector()).multiply(JUMPPAD_BOOST_VALUE).add(0.f, 1.5f, 0.f));
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}
}