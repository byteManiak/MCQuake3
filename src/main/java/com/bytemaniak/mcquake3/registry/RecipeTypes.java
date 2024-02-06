package com.bytemaniak.mcquake3.registry;

import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;

public class RecipeTypes {
    public static RecipeType<AbstractCookingRecipe> PLASMA_INDUCER_RECIPE_TYPE;

    public static void registerRecipeTypes() {
        PLASMA_INDUCER_RECIPE_TYPE = RecipeType.register("mcquake3:plasma_inducer");
    }
}
