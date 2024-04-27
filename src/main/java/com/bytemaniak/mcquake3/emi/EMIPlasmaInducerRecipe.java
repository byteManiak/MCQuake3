package com.bytemaniak.mcquake3.emi;

import com.bytemaniak.mcquake3.recipes.PlasmaInducerRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EMIPlasmaInducerRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> items;
    private final List<EmiStack> output;
    private final Identifier TEXTURE = new Identifier("mcquake3:textures/gui/plasma_inducer.png");

    public EMIPlasmaInducerRecipe(PlasmaInducerRecipe recipe) {
        id = recipe.getId();
        DefaultedList<Ingredient> ingredients = recipe.getIngredients();
        items = List.of(
                EmiIngredient.of(ingredients.get(0)), EmiIngredient.of(ingredients.get(1)),
                EmiIngredient.of(ingredients.get(2)), EmiIngredient.of(ingredients.get(3)));
        output = List.of(EmiStack.of(recipe.getOutput(null)));
    }
    @Override
    public EmiRecipeCategory getCategory() { return MCQuake3EMI.PLASMA_INDUCER_CATEGORY; }

    @Override
    public @Nullable Identifier getId() { return id; }

    @Override
    public List<EmiIngredient> getInputs() { return items; }

    @Override
    public List<EmiStack> getOutputs() { return output; }

    @Override
    public int getDisplayWidth() { return 152; }

    @Override
    public int getDisplayHeight() { return 104; }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(TEXTURE, 0, 0, 151, 90, 16, 14);
        widgets.addSlot(items.get(0), 48, 32).drawBack(false);
        widgets.addTexture(EmiTexture.FULL_FLAME, 50, 52);
        widgets.addSlot(items.get(1), 20, 15).drawBack(false);
        widgets.addSlot(items.get(2), 48, 3).drawBack(false);
        widgets.addSlot(items.get(3), 76, 15).drawBack(false);
        widgets.addSlot(EmiStack.of(Items.LAVA_BUCKET), 48, 68).drawBack(false);
        widgets.addSlot(output.get(0), 108, 50).drawBack(false);
    }
}
