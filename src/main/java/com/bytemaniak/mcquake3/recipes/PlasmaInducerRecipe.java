package com.bytemaniak.mcquake3.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class PlasmaInducerRecipe implements Recipe<RecipeInput> {
    private final List<Ingredient> items;
    public final ItemStack output;
    public final int cookingTime;

    public PlasmaInducerRecipe(List<Ingredient> ingredients, ItemStack itemStack, int cookingTime) {
        output = itemStack;
        items = ingredients;
        this.cookingTime = cookingTime;
    }

    @Override
    public boolean matches(RecipeInput inventory, World world) {
        if (world.isClient) return false;

        return items.get(0).test(inventory.getStackInSlot(0)) && items.get(1).test(inventory.getStackInSlot(3)) &&
                items.get(2).test(inventory.getStackInSlot(4)) && items.get(3).test(inventory.getStackInSlot(5));
    }

    @Override
    public ItemStack craft(RecipeInput input, RegistryWrapper.WrapperLookup lookup) { return output; }

    @Override
    public boolean fits(int width, int height) { return true; }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) { return output.copy(); }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(items.size());
        list.addAll(items);
        return list;
    }

    @Override
    public RecipeSerializer<?> getSerializer() { return null; }

    /*@Override
    public RecipeSerializer<?> getSerializer() {
        return PlasmaInducerRecipeSerializer.INSTANCE;
    }*/

    @Override
    public RecipeType<?> getType() { return PlasmaInducerType.INSTANCE; }

    public static class PlasmaInducerType implements RecipeType<PlasmaInducerRecipe> {
        private PlasmaInducerType() {}
        public static final PlasmaInducerType INSTANCE = new PlasmaInducerType();
    }

    /*public static class PlasmaInducerRecipeSerializer implements RecipeSerializer<PlasmaInducerRecipe> {
        public static final PlasmaInducerRecipeSerializer INSTANCE = new PlasmaInducerRecipeSerializer();
        public static final MapCodec<PlasmaInducerRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
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
        public MapCodec<PlasmaInducerRecipe> codec() { return CODEC; }

        @Override
        public PacketCodec<RegistryByteBuf, PlasmaInducerRecipe> packetCodec() {
            return null;
        }

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
    }*/
}
