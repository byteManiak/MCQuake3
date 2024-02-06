package com.bytemaniak.mcquake3.recipes;

import com.bytemaniak.mcquake3.registry.RecipeTypes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class PlasmaInducerRecipe implements Recipe<Inventory> {
    private final Identifier id;
    private final ItemStack output;
    private final List<Ingredient> items;

    public PlasmaInducerRecipe(Identifier id, List<Ingredient> ingredients, ItemStack itemStack) {
        this.id = id;
        output = itemStack;
        items = ingredients;
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
    public ItemStack getOutput(DynamicRegistryManager registryManager) { return output.copy(); }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(items.size());
        list.addAll(items);
        return list;
    }

    @Override
    public Identifier getId() { return id; }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return new PlasmaInducerRecipeSerializer();
    }

    @Override
    public RecipeType<?> getType() { return RecipeTypes.PLASMA_INDUCER_RECIPE_TYPE; }

    public static class PlasmaInducerRecipeSerializer implements RecipeSerializer<PlasmaInducerRecipe> {
        @Override
        public PlasmaInducerRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(4, Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) inputs.set(i, Ingredient.fromJson(ingredients.get(i)));

            return new PlasmaInducerRecipe(id, inputs, output);
        }

        @Override
        public PlasmaInducerRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            return new PlasmaInducerRecipe(id, inputs, output);
        }

        @Override
        public void write(PacketByteBuf buf, PlasmaInducerRecipe recipe) {
            buf.writeInt(recipe.items.size());
            for (Ingredient i : recipe.getIngredients()) i.write(buf);
            buf.writeItemStack(recipe.output);
        }
    }
}
