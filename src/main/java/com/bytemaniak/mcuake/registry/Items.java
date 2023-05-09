package com.bytemaniak.mcuake.registry;

import com.bytemaniak.mcuake.items.*;
import com.bytemaniak.mcuake.items.PlayerSettings;
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
    public static final Item PLAYER_SETTINGS = new PlayerSettings();

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

    public static final ItemGroup MCUAKE_GROUP = FabricItemGroup.builder(new Identifier("mcuake", "player_settings"))
            .icon(() -> new ItemStack(PLAYER_SETTINGS)).build();

    public static void loadItems() {
        loadItem(PLAYER_SETTINGS, new Identifier("mcuake", "player_settings"));

        loadItem(GAUNTLET, new Identifier("mcuake", "gauntlet"));
        loadItem(MACHINEGUN, new Identifier("mcuake", "machinegun"));
        loadItem(SHOTGUN, new Identifier("mcuake", "shotgun"));
        loadItem(GRENADE_LAUNCHER, new Identifier("mcuake", "grenade_launcher"));
        loadItem(LIGHTNING_GUN, new Identifier("mcuake", "lightning_gun"));
        loadItem(RAILGUN, new Identifier("mcuake", "railgun"));
        loadItem(PLASMAGUN, new Identifier("mcuake", "plasmagun"));
        loadItem(ROCKET_LAUNCHER, new Identifier("mcuake", "rocket_launcher"));

        loadItem(MACHINEGUN_AMMO, new Identifier("mcuake", "machinegun_ammo_box"));
        loadItem(SHOTGUN_AMMO, new Identifier("mcuake", "shotgun_ammo_box"));
        loadItem(GRENADE_AMMO, new Identifier("mcuake", "grenade_ammo_box"));
        loadItem(ROCKET_AMMO, new Identifier("mcuake", "rocket_ammo_box"));
        loadItem(LIGHTNING_AMMO, new Identifier("mcuake", "lightning_ammo_box"));
        loadItem(RAILGUN_AMMO, new Identifier("mcuake", "railgun_ammo_box"));
        loadItem(PLASMAGUN_AMMO, new Identifier("mcuake", "plasmagun_ammo_box"));
        loadItem(BFG_AMMO, new Identifier("mcuake", "bfg_ammo_box"));

        loadItem(TOOL, new Identifier("mcuake", "tool"));
    }

    // Load an item into the item registry and add it to the MCuake creative tab
    public static void loadItem(Item item, Identifier id) {
        Registry.register(Registries.ITEM, id, item);
        ItemGroupEvents.modifyEntriesEvent(MCUAKE_GROUP).register(content -> {
            content.add(item);
        });
    }
}
