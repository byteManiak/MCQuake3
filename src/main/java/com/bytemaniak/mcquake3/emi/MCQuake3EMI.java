package com.bytemaniak.mcquake3.emi;

import com.bytemaniak.mcquake3.recipes.PlasmaInducerRecipe;
import com.bytemaniak.mcquake3.registry.Blocks;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

public class MCQuake3EMI implements EmiPlugin {
    public static final EmiStack PLASMA_INDUCER = EmiStack.of(Blocks.PLASMA_INDUCER_BLOCK);
    public static final EmiRecipeCategory PLASMA_INDUCER_CATEGORY =
            new EmiRecipeCategory(new Identifier("mcquake3:plasma_inducer"), PLASMA_INDUCER);
    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(PLASMA_INDUCER_CATEGORY);

        registry.addWorkstation(PLASMA_INDUCER_CATEGORY, PLASMA_INDUCER);

        RecipeManager manager = registry.getRecipeManager();
        for (PlasmaInducerRecipe recipe : manager.listAllOfType(PlasmaInducerRecipe.PlasmaInducerType.INSTANCE))
            registry.addRecipe(new EMIPlasmaInducerRecipe(recipe));
    }
}
