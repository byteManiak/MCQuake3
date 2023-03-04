package com.bytemaniak.mcuake;

import com.bytemaniak.mcuake.registry.Packets;
import com.bytemaniak.mcuake.registry.Blocks;
import com.bytemaniak.mcuake.registry.Items;
import com.bytemaniak.mcuake.registry.MusicDiscs;
import com.bytemaniak.mcuake.registry.Sounds;
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

		Sounds.loadSounds();
		MusicDiscs.LoadDiscs();

		Packets.registerServerPackets();
	}
}