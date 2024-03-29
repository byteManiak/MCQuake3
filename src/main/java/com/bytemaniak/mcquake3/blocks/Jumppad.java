package com.bytemaniak.mcquake3.blocks;

import com.bytemaniak.mcquake3.items.Weapon;
import com.bytemaniak.mcquake3.registry.Sounds;
import com.bytemaniak.mcquake3.registry.Weapons;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
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

public class Jumppad extends HorizontalFacingBlock implements BlockEntityProvider {
	private static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 4, 16);

	private static final int JUMPPAD_BOOST_SOUND_TICKS_COOLDOWN = 10;

	public Jumppad() {
		super(FabricBlockSettings.of(Material.METAL).strength(1.5f, 5.0f));
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
		return getDefaultState().with(FACING, context.getHorizontalPlayerFacing());
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
	// Do nothing, so entity does not take fall damage if landing on the jumppad
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (entity instanceof PlayerEntity) {
			JumppadEntity ent = (JumppadEntity) world.getBlockEntity(pos);
			Direction direction = state.get(FACING);

			if (world.isClient) {
				entity.setOnGround(false);
				entity.setVelocity(Vec3d.of(direction.getVector()).multiply(ent.forward_power).add(0.f, ent.up_power / 4.f, 0.f));
				entity.velocityModified = true;
			} else {
				JumppadEntity jumppad = (JumppadEntity) world.getBlockEntity(pos);
				if (jumppad != null &&
					world.getTime() - ent.lastTick > JUMPPAD_BOOST_SOUND_TICKS_COOLDOWN) {
					world.playSound(null, pos, Sounds.JUMPPAD_BOOST, SoundCategory.BLOCKS, 1, 1);
					ent.lastTick = world.getTime();
				}
			}
		}
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack mainHandStack = player.getStackInHand(hand);
		if (!world.isClient && mainHandStack.isOf(Weapons.TOOL))
		{
			JumppadEntity jumppad = (JumppadEntity)world.getBlockEntity(pos);
			PlayerEntity lastPlayer = jumppad.getLastPlayerUser();
			if (lastPlayer == null || lastPlayer.currentScreenHandler != jumppad.getLastScreen())
			{
				NamedScreenHandlerFactory screenFactory = state.createScreenHandlerFactory(world, pos);
				if (screenFactory != null)
				{
					player.openHandledScreen(screenFactory);
				}
				// Ensure the jump pad's settings menu can only be accessed by one player
				// since the data in the GUI isn't properly synced in real time.
				jumppad.setLastPlayerUser(player);
			}
		}

		if (mainHandStack.getItem() instanceof Weapon) return ActionResult.PASS;
		else return ActionResult.SUCCESS;
	}
}