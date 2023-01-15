package com.bytemaniak.mcuake;

import com.bytemaniak.mcuake.blocks.Jumppad;
import com.bytemaniak.mcuake.items.Tool;
import com.bytemaniak.mcuake.registry.MusicDiscRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCuake implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("mcuake");

	public static final Block JUMPPAD_BLOCK = new Jumppad();

	@Override
	public void onInitialize() {
		Registry.register(Registries.BLOCK, new Identifier("mcuake", "jumppad"), JUMPPAD_BLOCK);
		Registry.register(Registries.ITEM, new Identifier("mcuake", "jumppad"), new BlockItem(JUMPPAD_BLOCK, new Item.Settings()));
		Registry.register(Registries.ITEM, new Identifier("mcuake", "tool"), new Tool());
		Registry.register(Registries.ITEM, new Identifier("mcuake", "machinegun"), new Item(new Item.Settings()));

		MusicDiscRegistry.LoadDiscs();
	}
}