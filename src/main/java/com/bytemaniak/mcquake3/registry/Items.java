package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.items.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Items {
    public static final Item MCQUAKE3_LOGO = new MCQuake3Logo();

    public static final Item TOOL = new Tool();

    public static final Item GAUNTLET = new Gauntlet();
    public static final Item MACHINEGUN = new Machinegun();
    public static final Item SHOTGUN = new Shotgun();
    public static final Item GRENADE_LAUNCHER = new GrenadeLauncher();
    public static final Item LIGHTNING_GUN = new LightningGun();
    public static final Item RAILGUN = new Railgun();
    public static final Item PLASMAGUN = new Plasmagun();
    public static final Item ROCKET_LAUNCHER = new RocketLauncher();

    public static final Item MACHINEGUN_AMMO = new Item(new FabricItemSettings());
    public static final Item SHOTGUN_AMMO = new Item(new FabricItemSettings());
    public static final Item GRENADE_AMMO = new Item(new FabricItemSettings());
    public static final Item ROCKET_AMMO = new Item(new FabricItemSettings());
    public static final Item LIGHTNING_AMMO = new Item(new FabricItemSettings());
    public static final Item RAILGUN_AMMO = new Item(new FabricItemSettings());
    public static final Item PLASMAGUN_AMMO = new Item(new FabricItemSettings());
    public static final Item BFG_AMMO = new Item(new FabricItemSettings());

    public static final ItemGroup MCQUAKE3_GROUP = FabricItemGroup.builder(new Identifier("mcquake3", "mcquake3_logo"))
            .icon(() -> new ItemStack(MCQUAKE3_LOGO)).build();

    public static void loadItems() {
        Registry.register(Registries.ITEM, new Identifier("mcquake3", "mcquake3_logo"), MCQUAKE3_LOGO);

        loadItem(GAUNTLET, new Identifier("mcquake3", "gauntlet"));
        loadItem(MACHINEGUN, new Identifier("mcquake3", "machinegun"));
        loadItem(SHOTGUN, new Identifier("mcquake3", "shotgun"));
        loadItem(GRENADE_LAUNCHER, new Identifier("mcquake3", "grenade_launcher"));
        loadItem(LIGHTNING_GUN, new Identifier("mcquake3", "lightning_gun"));
        loadItem(RAILGUN, new Identifier("mcquake3", "railgun"));
        loadItem(PLASMAGUN, new Identifier("mcquake3", "plasmagun"));
        loadItem(ROCKET_LAUNCHER, new Identifier("mcquake3", "rocket_launcher"));

        loadItem(MACHINEGUN_AMMO, new Identifier("mcquake3", "machinegun_ammo_box"));
        loadItem(SHOTGUN_AMMO, new Identifier("mcquake3", "shotgun_ammo_box"));
        loadItem(GRENADE_AMMO, new Identifier("mcquake3", "grenade_ammo_box"));
        loadItem(ROCKET_AMMO, new Identifier("mcquake3", "rocket_ammo_box"));
        loadItem(LIGHTNING_AMMO, new Identifier("mcquake3", "lightning_ammo_box"));
        loadItem(RAILGUN_AMMO, new Identifier("mcquake3", "railgun_ammo_box"));
        loadItem(PLASMAGUN_AMMO, new Identifier("mcquake3", "plasmagun_ammo_box"));
        loadItem(BFG_AMMO, new Identifier("mcquake3", "bfg_ammo_box"));

        loadItem(TOOL, new Identifier("mcquake3", "tool"));
    }

    // Load an item into the item registry and add it to the MCQuake3 creative tab
    public static void loadItem(Item item, Identifier id) {
        Registry.register(Registries.ITEM, id, item);
        ItemGroupEvents.modifyEntriesEvent(MCQUAKE3_GROUP).register(content -> {
            content.add(item);
        });
    }
}