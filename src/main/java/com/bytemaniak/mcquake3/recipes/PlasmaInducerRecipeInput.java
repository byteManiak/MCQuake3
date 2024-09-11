package com.bytemaniak.mcquake3.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record PlasmaInducerRecipeInput(ItemStack i1, ItemStack i2, ItemStack i3, ItemStack i4) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        return switch (slot) {
            case 0 -> i1;
            case 1 -> i2;
            case 2 -> i3;
            case 3 -> i4;
			default -> throw new IllegalArgumentException("Recipe does not contain slot " + slot);
        };
    }

    @Override
    public int getSize() { return 4; }

	@Override
	public boolean isEmpty() {
		return i1.isEmpty() && i2.isEmpty() && i3.isEmpty() && i4.isEmpty();
	}
}
