package com.bytemaniak.mcuake;

import com.bytemaniak.mcuake.blocks.Jumppad;
import com.bytemaniak.mcuake.blocks.Spikes;
import com.bytemaniak.mcuake.items.Tool;
import com.bytemaniak.mcuake.registry.MusicDiscRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
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
	public static final Block SPIKES_BLOCK = new Spikes();

	@Override
	public void onInitialize() {
		loadDefaultBlock(SPIKES_BLOCK, new Identifier("mcuake", "spikes"));
		loadDefaultBlock(JUMPPAD_BLOCK, new Identifier("mcuake", "jumppad"));
		Registry.register(Registries.ITEM, new Identifier("mcuake", "tool"), new Tool());
		Registry.register(Registries.ITEM, new Identifier("mcuake", "machinegun"), new Item(new Item.Settings()));

		MusicDiscRegistry.LoadDiscs();
	}

	/* Load a block into a registry and create a default item for it */
	private void loadDefaultBlock(Block block, Identifier id)
	{
		Registry.register(Registries.BLOCK, id, block);
		Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
	}
}