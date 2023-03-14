package com.bytemaniak.mcuake;

import com.bytemaniak.mcuake.registry.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class MCuake implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("mcuake");

	@Override
	public void onInitialize() {
		GeckoLib.initialize();

		Items.loadItems();
		Blocks.loadBlocks();
		Entities.loadEntities();

		Sounds.loadSounds();
		MusicDiscs.LoadDiscs();

		Packets.registerServerPackets();
	}
}