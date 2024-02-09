package com.bytemaniak.mcquake3.blocks;

import com.bytemaniak.mcquake3.MCQuake3;
import com.bytemaniak.mcquake3.recipes.PlasmaInducerRecipe;
import com.bytemaniak.mcquake3.registry.Blocks;
import com.bytemaniak.mcquake3.screen.PlasmaInducerScreenHandler;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PlasmaInducerEntity extends AbstractFurnaceBlockEntity {
    public static final int BURN_TIME_PROPERTY_INDEX = 0;
    public static final int FUEL_TIME_PROPERTY_INDEX = 1;
    public static final int COOK_TIME_PROPERTY_INDEX = 2;
    public static final int COOK_TIME_TOTAL_PROPERTY_INDEX = 3;

    public PlasmaInducerEntity(BlockPos pos, BlockState state) {
        super(Blocks.PLASMA_INDUCER_BLOCK_ENTITY, pos, state, null);
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
        return 1000;
    }

    private static boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, PlasmaInducerRecipe recipe, DefaultedList<ItemStack> slots) {
        if (slots.get(0).isEmpty() || slots.get(3).isEmpty() || slots.get(4).isEmpty() || slots.get(5).isEmpty() || recipe == null)
            return false;

        ItemStack itemStack = recipe.getResult(registryManager);
        if (itemStack.isEmpty()) return false;

        ItemStack outputStack = slots.get(2);
        if (outputStack.isEmpty()) return true;

        if (!ItemStack.areItemsEqual(outputStack, itemStack)) return false;

        if (outputStack.getCount() < 64)
            return true;

        return outputStack.getCount() < itemStack.getMaxCount();
    }

    private static boolean craftRecipe(DynamicRegistryManager registryManager, PlasmaInducerRecipe recipe, DefaultedList<ItemStack> slots) {
        if (recipe == null || !canAcceptRecipeOutput(registryManager, recipe, slots)) return false;

        ItemStack itemStack = slots.get(0);
        ItemStack itemStack2 = slots.get(3);
        ItemStack itemStack3 = slots.get(4);
        ItemStack itemStack4 = slots.get(5);

        ItemStack outputSlot = slots.get(2);
        ItemStack output = recipe.getResult(registryManager);

        if (outputSlot.isEmpty()) slots.set(2, output.copy());
        else if (outputSlot.isOf(output.getItem())) outputSlot.increment(1);

        itemStack.decrement(1);
        itemStack2.decrement(1);
        itemStack3.decrement(1);
        itemStack4.decrement(1);

        return true;
    }

    public static void tick(World world, BlockPos pos, BlockState state, PlasmaInducerEntity blockEntity) {
        RecipeManager.MatchGetter<Inventory, PlasmaInducerRecipe> getter =
                RecipeManager.createCachedMatchGetter(PlasmaInducerRecipe.PlasmaInducerType.INSTANCE);
        int burnTime = blockEntity.propertyDelegate.get(BURN_TIME_PROPERTY_INDEX);
        boolean isBurning = burnTime > 0;
        boolean dirty = false;

        if (isBurning) blockEntity.propertyDelegate.set(BURN_TIME_PROPERTY_INDEX, burnTime-1);

        ItemStack itemStack = blockEntity.inventory.get(1);
        boolean hasFuel = itemStack.isOf(Items.LAVA_BUCKET);
        boolean hasIngredients =
                !blockEntity.inventory.get(0).isEmpty() && !blockEntity.inventory.get(3).isEmpty() &&
                !blockEntity.inventory.get(4).isEmpty() && !blockEntity.inventory.get(5).isEmpty();

        if (isBurning || (hasFuel && hasIngredients)) {
            RecipeEntry<PlasmaInducerRecipe> recipeEntry = getter.getFirstMatch(blockEntity, world).orElse(null);
            PlasmaInducerRecipe recipe = null;
            if (recipeEntry != null) recipe = recipeEntry.value();

            if (canAcceptRecipeOutput(world.getRegistryManager(), recipe, blockEntity.inventory))
                blockEntity.propertyDelegate.set(COOK_TIME_TOTAL_PROPERTY_INDEX, recipe.cookingTime);

            if (!isBurning && canAcceptRecipeOutput(world.getRegistryManager(), recipe, blockEntity.inventory)) {
                blockEntity.propertyDelegate.set(BURN_TIME_PROPERTY_INDEX, blockEntity.getFuelTime(itemStack));
                blockEntity.propertyDelegate.set(FUEL_TIME_PROPERTY_INDEX, blockEntity.getFuelTime(itemStack));
                dirty = true;

                Item item = itemStack.getItem();
                itemStack.decrement(1);
                if (itemStack.isEmpty()) {
                    Item item2 = item.getRecipeRemainder();
                    blockEntity.inventory.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                }
            }
            if (isBurning && canAcceptRecipeOutput(world.getRegistryManager(), recipe, blockEntity.inventory)) {
                int cookTime = blockEntity.propertyDelegate.get(COOK_TIME_PROPERTY_INDEX);
                int cookTimeTotal = blockEntity.propertyDelegate.get(COOK_TIME_TOTAL_PROPERTY_INDEX);
                ++cookTime;
                blockEntity.propertyDelegate.set(COOK_TIME_PROPERTY_INDEX, cookTime);
                if (cookTime == cookTimeTotal) {
                    blockEntity.propertyDelegate.set(COOK_TIME_PROPERTY_INDEX, 0);
                    if (craftRecipe(world.getRegistryManager(), recipe, blockEntity.inventory)) {
                        blockEntity.setLastRecipe(recipeEntry);
                    }
                }
                dirty = true;
            } else {
                blockEntity.propertyDelegate.set(COOK_TIME_PROPERTY_INDEX, 0);
            }
        } else if (!isBurning && blockEntity.propertyDelegate.get(COOK_TIME_PROPERTY_INDEX) > 0) {
            int cookTime = blockEntity.propertyDelegate.get(COOK_TIME_PROPERTY_INDEX);
            blockEntity.propertyDelegate.set(2, MathHelper.clamp(cookTime - 2, 0, blockEntity.propertyDelegate.get(3)));
        }

        if (isBurning != (blockEntity.propertyDelegate.get(BURN_TIME_PROPERTY_INDEX) > 0)) {
            dirty = true;
            state = state.with(AbstractFurnaceBlock.LIT, blockEntity.propertyDelegate.get(BURN_TIME_PROPERTY_INDEX) > 0);
            world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }

        if (dirty) AbstractFurnaceBlockEntity.markDirty(world, pos, state);
    }

    @Override
    public List<RecipeEntry<?>> getRecipesUsedAndDropExperience(ServerWorld world, Vec3d pos) { return new ArrayList<>(); }
}
