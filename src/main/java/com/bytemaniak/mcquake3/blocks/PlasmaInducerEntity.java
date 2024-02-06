package com.bytemaniak.mcquake3.blocks;

import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.registry.RecipeTypes;
import com.bytemaniak.mcquake3.screen.PlasmaInducerScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class PlasmaInducerEntity extends AbstractFurnaceBlockEntity {
    public PlasmaInducerEntity(BlockPos pos, BlockState state) {
        super(Blocks.PLASMA_INDUCER_BLOCK_ENTITY, pos, state, RecipeTypes.PLASMA_INDUCER_RECIPE_TYPE);
        inventory = DefaultedList.ofSize(6, ItemStack.EMPTY);
    }

    @Override
    protected Text getContainerName() { return Text.translatable(getCachedState().getBlock().getTranslationKey()); }

    @Override
    public int size() { return 6; }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new PlasmaInducerScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    protected int getFuelTime(ItemStack fuel) {
        if (!fuel.isOf(Items.LAVA_BUCKET)) return 0;

        return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(fuel, 0);
    }
}
