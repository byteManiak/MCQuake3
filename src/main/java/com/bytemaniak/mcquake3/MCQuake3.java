package com.bytemaniak.mcquake3;

import com.bytemaniak.mcquake3.registry.*;
import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class MCQuake3 implements ModInitializer, PreLaunchEntrypoint {
	public static final Logger LOGGER = LoggerFactory.getLogger("mcquake3");

	@Override
	public void onInitialize() {
		GeckoLib.initialize();

		RecipeTypes.registerRecipeTypes();
		Weapons.loadItems();
		CraftingItems.loadCraftingItems();
		Blocks.loadBlocks();
		Entities.loadEntities();
		Q3StatusEffects.registerEffects();

		Sounds.loadSounds();
		MusicDiscs.loadDiscs();

		Screens.registerScreenHandlers();

		Packets.registerServerPackets();
	}

	@Override
	public void onPreLaunch() {
		MixinExtrasBootstrap.init();
	}
}