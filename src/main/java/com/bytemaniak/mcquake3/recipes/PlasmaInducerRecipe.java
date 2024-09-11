package com.bytemaniak.mcquake3.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public record PlasmaInducerRecipe(List<Ingredient> ingredients, ItemStack output, int cookingTime) implements Recipe<PlasmaInducerRecipeInput> {
    @Override
    public boolean matches(PlasmaInducerRecipeInput inventory, World world) {
        if (world.isClient) return false;

        return ingredients.get(0).test(inventory.getStackInSlot(0)) && ingredients.get(1).test(inventory.getStackInSlot(1)) &&
               ingredients.get(2).test(inventory.getStackInSlot(2)) && ingredients.get(3).test(inventory.getStackInSlot(3));
    }

    @Override
    public ItemStack craft(PlasmaInducerRecipeInput input, RegistryWrapper.WrapperLookup lookup) { return output; }

    @Override
    public boolean fits(int width, int height) { return true; }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) { return output.copy(); }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(ingredients.size());
        list.addAll(ingredients);
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
        public static final MapCodec<PlasmaInducerRecipe> CODEC = RecordCodecBuilder.mapCodec(in -> in.group(
                validateAmount().fieldOf("ingredients").forGetter(PlasmaInducerRecipe::getIngredients),
                ItemStack.VALIDATED_CODEC.fieldOf("output").forGetter(PlasmaInducerRecipe::output),
                Codec.INT.fieldOf("cookingtime").orElse(200).forGetter(PlasmaInducerRecipe::cookingTime)
        ).apply(in, PlasmaInducerRecipe::new));

        public static final PacketCodec<RegistryByteBuf, PlasmaInducerRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                PlasmaInducerRecipe.PlasmaInducerRecipeSerializer::write, PlasmaInducerRecipe.PlasmaInducerRecipeSerializer::read
        );

        private static Codec<List<Ingredient>> validateAmount() {
            return Ingredient.DISALLOW_EMPTY_CODEC.sizeLimitedListOf(4).validate(list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
        }

        @Override
        public MapCodec<PlasmaInducerRecipe> codec() { return CODEC; }

        @Override
        public PacketCodec<RegistryByteBuf, PlasmaInducerRecipe> packetCodec() { return PACKET_CODEC; }

        public static PlasmaInducerRecipe read(RegistryByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.PACKET_CODEC.decode(buf));

            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            return new PlasmaInducerRecipe(inputs, output, buf.readInt());
        }

        public static void write(RegistryByteBuf buf, PlasmaInducerRecipe recipe) {
            buf.writeInt(recipe.ingredients.size());
            for (Ingredient i : recipe.getIngredients()) Ingredient.PACKET_CODEC.encode(buf, i);
            ItemStack.PACKET_CODEC.encode(buf, recipe.output);
            buf.writeInt(recipe.cookingTime);
        }
    }
}
