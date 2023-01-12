package com.bytemaniak.mcuake;

import com.bytemaniak.mcuake.blocks.MCuakeJumppad;
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

	public static final Block JUMPPAD_BLOCK = new MCuakeJumppad();

	@Override
	public void onInitialize() {
		Registry.register(Registries.BLOCK, new Identifier("mcuake", "jumppad"), JUMPPAD_BLOCK);
		Registry.register(Registries.ITEM, new Identifier("mcuake", "jumppad"), new BlockItem(JUMPPAD_BLOCK, new Item.Settings()));
	}
}