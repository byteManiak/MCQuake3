package com.bytemaniak.mcuake.blocks.jumppad;

import com.bytemaniak.mcuake.MCuake;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.UUID;

public class Jumppad extends HorizontalFacingBlock implements BlockEntityProvider {
	private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 4, 16);

	private UUID lastPlayerUser;

	public Jumppad() {
		super(FabricBlockSettings.of(Material.METAL));
		lastPlayerUser = UUID.randomUUID();
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) { return SHAPE; }

	@Override
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory)blockEntity : null;
	}
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return this.getDefaultState().with(FACING, context.getPlayerFacing());
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
	{
		return new JumppadEntity(pos, state);
	}

	@Override
	public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
		JumppadEntity ent = (JumppadEntity) world.getBlockEntity(pos);

		Direction direction = state.get(FACING);
		entity.addVelocity(Vec3d.of(direction.getVector()).multiply(ent.forward_power / 3.f).add(0.f, ent.up_power / 3.f, 0.f));
	}

	@Override
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		// Do nothing, so entity does not take fall damage if landing on the jumppad
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient && player.getMainHandStack().isOf(MCuake.TOOL))
		{
			PlayerEntity lastPlayer = world.getPlayerByUuid(lastPlayerUser);
			if (lastPlayer == null || !(lastPlayer.currentScreenHandler instanceof JumppadScreenHandler))
			{
				NamedScreenHandlerFactory screenFactory = state.createScreenHandlerFactory(world, pos);
				if (screenFactory != null)
				{
					player.openHandledScreen(screenFactory);
				}
				// Ensure the jump pad's settings menu can only be accessed by one player
				// since the data in the GUI isn't properly synced in real time.
				lastPlayerUser = player.getUuid();
			}

			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}
}