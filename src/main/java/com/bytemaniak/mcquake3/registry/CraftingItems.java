package com.bytemaniak.mcquake3.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CraftingItems {
    public static final Item IRON_ROD = new Item(new FabricItemSettings());
    public static final Item COPPER_NUGGET = new Item(new FabricItemSettings());

    public static final Item GUN_HANDLE = new Item(new FabricItemSettings());
    public static final Item GUN_TRIGGER = new Item(new FabricItemSettings());
    public static final Item MAGAZINE_HOLDER = new Item(new FabricItemSettings());
    public static final Item GUN_BASE = new Item(new FabricItemSettings());
    public static final Item BARREL = new Item(new FabricItemSettings());
    public static final Item ROTATING_SHAFT = new Item(new FabricItemSettings());
    public static final Item REINFORCED_BARREL = new Item(new FabricItemSettings());
    public static final Item ENERGY_CHAMBER = new Item(new FabricItemSettings());

    public static final Item GAUNTLET_BLADE = new Item(new FabricItemSettings());

    public static final RegistryKey<ItemGroup> MCQUAKE3_CRAFTING_GROUP =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("mcquake3:mcquake3_crafting"));

    // Load an item into the item registry and add it to the MCQuake3 creative tab
    private static void loadItem(Item item, Identifier id) {
        Registry.register(Registries.ITEM, id, item);
        ItemGroupEvents.modifyEntriesEvent(MCQUAKE3_CRAFTING_GROUP).register(content -> content.add(item));
    }

    public static void loadCraftingItems() {
        Registry.register(Registries.ITEM_GROUP, MCQUAKE3_CRAFTING_GROUP,
                FabricItemGroup.builder().icon(() -> new ItemStack(GUN_BASE))
                        .displayName(Text.translatable("itemGroup.mcquake3.mcquake3_crafting")).build());

        loadItem(IRON_ROD, new Identifier("mcquake3:iron_rod"));
        loadItem(COPPER_NUGGET, new Identifier("mcquake3:copper_nugget"));
        loadItem(GUN_HANDLE, new Identifier("mcquake3:gun_handle"));
        loadItem(GUN_TRIGGER, new Identifier("mcquake3:gun_trigger"));
        loadItem(MAGAZINE_HOLDER, new Identifier("mcquake3:magazine_holder"));
        loadItem(GUN_BASE, new Identifier("mcquake3:gun_base"));
        loadItem(BARREL, new Identifier("mcquake3:barrel"));
        loadItem(ROTATING_SHAFT, new Identifier("mcquake3:rotating_shaft"));
        loadItem(REINFORCED_BARREL, new Identifier("mcquake3:reinforced_barrel"));
        loadItem(ENERGY_CHAMBER, new Identifier("mcquake3:energy_chamber"));
        loadItem(GAUNTLET_BLADE, new Identifier("mcquake3:gauntlet_blade"));
    }
}
