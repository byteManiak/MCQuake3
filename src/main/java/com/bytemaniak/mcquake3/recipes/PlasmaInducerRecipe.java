package com.bytemaniak.mcquake3.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.List;

public class PlasmaInducerRecipe implements Recipe<Inventory> {
    private final List<Ingredient> items;
    public final ItemStack output;
    public final int cookingTime;

    public PlasmaInducerRecipe(List<Ingredient> ingredients, ItemStack itemStack, int cookingTime) {
        output = itemStack;
        items = ingredients;
        this.cookingTime = cookingTime;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        if (world.isClient) return false;

        return items.get(0).test(inventory.getStack(0)) && items.get(1).test(inventory.getStack(3)) &&
                items.get(2).test(inventory.getStack(4)) && items.get(3).test(inventory.getStack(5));
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) { return output; }

    @Override
    public boolean fits(int width, int height) { return true; }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) { return output.copy(); }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(items.size());
        list.addAll(items);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PlasmaInducerRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() { return PlasmaInducerType.INSTANCE; }

    public static class PlasmaInducerType implements RecipeType<PlasmaInducerRecipe> {
        private PlasmaInducerType() {}
        public static final PlasmaInducerType INSTANCE = new PlasmaInducerType();
    }

    public static class PlasmaInducerRecipeSerializer implements RecipeSerializer<PlasmaInducerRecipe> {
        public static final PlasmaInducerRecipeSerializer INSTANCE = new PlasmaInducerRecipeSerializer();
        public static final Codec<PlasmaInducerRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 4).fieldOf("ingredients").forGetter(PlasmaInducerRecipe::getIngredients),
                ItemStack.RECIPE_RESULT_CODEC.fieldOf("output").forGetter(r -> r.output),
                Codec.INT.fieldOf("cookingtime").orElse(200).forGetter(r -> r.cookingTime)
        ).apply(in, PlasmaInducerRecipe::new));

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max) {
            return Codecs.validate(Codecs.validate(
                    delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many ingredients!") : DataResult.success(list)
            ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
        }

        @Override
        public Codec<PlasmaInducerRecipe> codec() { return CODEC; }

        @Override
        public PlasmaInducerRecipe read(PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            return new PlasmaInducerRecipe(inputs, output, buf.readInt());
        }

        @Override
        public void write(PacketByteBuf buf, PlasmaInducerRecipe recipe) {
            buf.writeInt(recipe.items.size());
            for (Ingredient i : recipe.getIngredients()) i.write(buf);
            buf.writeItemStack(recipe.output);
            buf.writeInt(recipe.cookingTime);
        }
    }
}
