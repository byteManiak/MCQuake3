package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.blocks.Spikes;
import com.bytemaniak.mcquake3.blocks.Jumppad;
import com.bytemaniak.mcquake3.blocks.JumppadEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.bytemaniak.mcquake3.registry.Items.loadItem;

public class Blocks {
    private static final Identifier JUMPPAD = new Identifier("mcquake3", "jumppad");
    public static final Block JUMPPAD_BLOCK = new Jumppad();
    public static final BlockEntityType<JumppadEntity> JUMPPAD_BLOCK_ENTITY;

    public static final Block SPIKES_BLOCK = new Spikes();

    static {
        JUMPPAD_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, JUMPPAD,
                FabricBlockEntityTypeBuilder.create(JumppadEntity::new, JUMPPAD_BLOCK).build());
    }

    public static void loadBlocks() {
        loadDefaultBlock(SPIKES_BLOCK, new Identifier("mcquake3", "spikes"));
        loadDefaultBlock(JUMPPAD_BLOCK, new Identifier("mcquake3", "jumppad"));
    }

    // Load a block into the block registry and create a default item for it
    private static void loadDefaultBlock(Block block, Identifier id) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.BLOCK, id, block);
        loadItem(blockItem, id);
    }
}
