package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.recipes.PlasmaInducerRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RecipeTypes {
    public static void registerRecipeTypes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("mcquake3:plasma_inducer"),
                PlasmaInducerRecipe.PlasmaInducerRecipeSerializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, new Identifier("mcquake3:plasma_inducer"),
                PlasmaInducerRecipe.PlasmaInducerType.INSTANCE);
    }
}
