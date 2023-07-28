package com.bytemaniak.mcquake3;

import com.bytemaniak.mcquake3.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class MCQuake3 implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("mcquake3");

	@Override
	public void onInitialize() {
		GeckoLib.initialize();

		Items.loadItems();
		Blocks.loadBlocks();
		Entities.loadEntities();

		Sounds.loadSounds();
		MusicDiscs.LoadDiscs();

		Screens.loadScreens();

		Packets.registerServerPackets();
	}
}