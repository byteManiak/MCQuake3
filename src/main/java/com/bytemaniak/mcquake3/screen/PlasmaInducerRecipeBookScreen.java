package com.bytemaniak.mcquake3.screen;

import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Collections;
import java.util.Set;

public class PlasmaInducerRecipeBookScreen extends AbstractFurnaceRecipeBookScreen {
    @Override
    protected Set<Item> getAllowedFuels() { return Collections.singleton(Items.LAVA_BUCKET); }
}
