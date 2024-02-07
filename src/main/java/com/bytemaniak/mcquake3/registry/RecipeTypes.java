package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.recipes.PlasmaInducerRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RecipeTypes {
    public static RecipeType<PlasmaInducerRecipe> PLASMA_INDUCER_RECIPE_TYPE;

    public static void registerRecipeTypes() {
        PLASMA_INDUCER_RECIPE_TYPE = RecipeType.register("mcquake3:plasma_inducer");
        Registry.register(Registries.RECIPE_SERIALIZER,
                new Identifier("mcquake3:plasma_inducer"), new PlasmaInducerRecipe.PlasmaInducerRecipeSerializer());
    }
}
