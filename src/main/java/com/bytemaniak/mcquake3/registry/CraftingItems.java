package com.bytemaniak.mcquake3.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class CraftingItems {
    public static final Item IRON_ROD = new Item(new FabricItemSettings());

    public static final Item GUN_HANDLE = new Item(new FabricItemSettings());
    public static final Item GUN_TRIGGER = new Item(new FabricItemSettings());
    public static final Item MAGAZINE_HOLDER = new Item(new FabricItemSettings());
    public static final Item GUN_BASE = new Item(new FabricItemSettings());

    public static final Item SHOTGUN_BARREL = new Item(new FabricItemSettings());

    private static final ItemGroup MCQUAKE3_CRAFTING_GROUP = FabricItemGroup.builder(new Identifier("mcquake3:mcquake3_crafting"))
            .icon(() -> new ItemStack(GUN_BASE)).build();

    // Load an item into the item registry and add it to the MCQuake3 creative tab
    private static void loadItem(Item item, Identifier id) {
        Registry.register(Registries.ITEM, id, item);
        ItemGroupEvents.modifyEntriesEvent(MCQUAKE3_CRAFTING_GROUP).register(content -> content.add(item));
    }

    public static void loadCraftingItems() {
        loadItem(IRON_ROD, new Identifier("mcquake3:iron_rod"));
        loadItem(GUN_HANDLE, new Identifier("mcquake3:gun_handle"));
        loadItem(GUN_TRIGGER, new Identifier("mcquake3:gun_trigger"));
        loadItem(MAGAZINE_HOLDER, new Identifier("mcquake3:magazine_holder"));
        loadItem(GUN_BASE, new Identifier("mcquake3:gun_base"));
        loadItem(SHOTGUN_BARREL, new Identifier("mcquake3:shotgun_barrel"));
    }
}
