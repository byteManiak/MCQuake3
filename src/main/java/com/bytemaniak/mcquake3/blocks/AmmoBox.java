package com.bytemaniak.mcquake3.blocks;

import com.bytemaniak.mcquake3.entity.QuakePlayer;
import com.bytemaniak.mcquake3.registry.Sounds;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class AmmoBox extends Block implements BlockEntityProvider {
    private static final long AMMO_BOX_RESPAWN_TIME = 300;
    private static final IntProperty TYPE = IntProperty.of("type", 1, 8);

    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 10, 15);

    public AmmoBox() {
        super(FabricBlockSettings.of(Material.METAL).noCollision());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE);
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AmmoBoxEntity(pos, state);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        AmmoBoxEntity ammoBox = (AmmoBoxEntity)world.getBlockEntity(pos);
        if (world.getTime() - ammoBox.lastTick > AMMO_BOX_RESPAWN_TIME && entity instanceof PlayerEntity player) {
            QuakePlayer qPlayer = (QuakePlayer)player;
            if (qPlayer.getAmmo(QuakePlayer.WeaponSlot.MACHINEGUN) < 200) {
                qPlayer.addAmmo(20, QuakePlayer.WeaponSlot.MACHINEGUN);
                ammoBox.lastTick = world.getTime();
                world.playSoundFromEntity(null, entity, Sounds.AMMO_PICKUP, SoundCategory.NEUTRAL, 1, 1);
            }
        }

    }
}
