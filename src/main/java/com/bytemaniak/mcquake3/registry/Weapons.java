package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.items.*;
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

public class Weapons {
    public static final Item TOOL = new Tool();
    public static final Item ARENA_TOOL = new ArenaTool();

    public static final Item BULLET = new Item(new Item.Settings());
    public static final Item SHELL = new Item(new Item.Settings());
    public static final Item GRENADE = new Item(new Item.Settings());
    public static final Item ROCKET = new Item(new Item.Settings());
    public static final Item LIGHTNING_CELL = new Item(new Item.Settings());
    public static final Item RAILGUN_ROUND = new Item(new Item.Settings());
    public static final Item PLASMA_BALL = new Item(new Item.Settings());
    public static final Item BFG10K_ROUND = new Item(new Item.Settings());

    public static final Weapon GAUNTLET = new Gauntlet();
    public static final Weapon MACHINEGUN = new Machinegun();
    public static final Weapon SHOTGUN = new Shotgun();
    public static final Weapon GRENADE_LAUNCHER = new GrenadeLauncher();
    public static final Weapon ROCKET_LAUNCHER = new RocketLauncher();
    public static final Weapon LIGHTNING_GUN = new LightningGun();
    public static final Weapon RAILGUN = new Railgun();
    public static final Weapon PLASMAGUN = new Plasmagun();
    public static final Weapon BFG10K = new BFG10K();

    public static final RegistryKey<ItemGroup> MCQUAKE3_WEAPONS_GROUP =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of("mcquake3:mcquake3_weapons"));

    public static void loadItems() {
        Registry.register(Registries.ITEM_GROUP, MCQUAKE3_WEAPONS_GROUP,
                FabricItemGroup.builder().icon(() -> new ItemStack(MACHINEGUN))
                        .displayName(Text.translatable("itemGroup.mcquake3.mcquake3_weapons")).build());

        loadItem(GAUNTLET, Identifier.of("mcquake3:gauntlet"));

        loadItem(BULLET, Identifier.of("mcquake3:bullet"));
        loadItem(MACHINEGUN, Identifier.of("mcquake3:machinegun"));

        loadItem(SHELL, Identifier.of("mcquake3:shell"));
        loadItem(SHOTGUN, Identifier.of("mcquake3:shotgun"));

        loadItem(GRENADE, Identifier.of("mcquake3:grenade"));
        loadItem(GRENADE_LAUNCHER, Identifier.of("mcquake3:grenade_launcher"));

        loadItem(ROCKET, Identifier.of("mcquake3:rocket"));
        loadItem(ROCKET_LAUNCHER, Identifier.of("mcquake3:rocket_launcher"));

        loadItem(LIGHTNING_CELL, Identifier.of("mcquake3:lightning_cell"));
        loadItem(LIGHTNING_GUN, Identifier.of("mcquake3:lightning_gun"));

        loadItem(RAILGUN_ROUND, Identifier.of("mcquake3:railgun_round"));
        loadItem(RAILGUN, Identifier.of("mcquake3:railgun"));

        loadItem(PLASMA_BALL, Identifier.of("mcquake3:plasma_ball"));
        loadItem(PLASMAGUN, Identifier.of("mcquake3:plasmagun"));

        loadItem(BFG10K_ROUND, Identifier.of("mcquake3:bfg10k_round"));
        loadItem(BFG10K, Identifier.of("mcquake3:bfg10k"));

        loadItem(TOOL, Identifier.of("mcquake3:tool"));
        loadItem(ARENA_TOOL, Identifier.of("mcquake3:arena_tool"));
    }

    // Load an item into the item registry and add it to the MCQuake3 creative tab
    public static void loadItem(Item item, Identifier id) {
        Registry.register(Registries.ITEM, id, item);
        ItemGroupEvents.modifyEntriesEvent(MCQUAKE3_WEAPONS_GROUP).register(content -> content.add(item));
    }
}
