package com.bytemaniak.mcuake;

import com.bytemaniak.mcuake.blocks.jumppad.Jumppad;
import com.bytemaniak.mcuake.blocks.jumppad.JumppadEntity;
import com.bytemaniak.mcuake.blocks.jumppad.JumppadScreenHandler;
import com.bytemaniak.mcuake.blocks.Spikes;
import com.bytemaniak.mcuake.cs.CSMessages;
import com.bytemaniak.mcuake.entity.projectile.Bullet;
import com.bytemaniak.mcuake.entity.projectile.PlasmaBall;
import com.bytemaniak.mcuake.items.Machinegun;
import com.bytemaniak.mcuake.items.Plasmagun;
import com.bytemaniak.mcuake.items.Railgun;
import com.bytemaniak.mcuake.items.Tool;
import com.bytemaniak.mcuake.registry.MusicDiscRegistry;
import com.bytemaniak.mcuake.registry.Sounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MCuake implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("mcuake");

	public static final Identifier JUMPPAD = new Identifier("mcuake", "jumppad");
	public static final Block JUMPPAD_BLOCK = new Jumppad();
	public static final BlockEntityType<JumppadEntity> JUMPPAD_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, JUMPPAD, FabricBlockEntityTypeBuilder.create(JumppadEntity::new, JUMPPAD_BLOCK).build());
	public static final ScreenHandlerType<JumppadScreenHandler> JUMPPAD_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(JumppadScreenHandler::new);

	public static final Block SPIKES_BLOCK = new Spikes();

	public static final Item PLAYER_SETTINGS = new Item(new Item.Settings());
	public static final Item TOOL = new Tool();
	public static final Item MACHINEGUN = new Machinegun();
	public static final Item PLASMAGUN = new Plasmagun();
	public static final Item RAILGUN = new Railgun();

	public static final EntityType<PlasmaBall> PLASMA_BALL = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier("mcuake", "plasmaball"),
			FabricEntityTypeBuilder.<PlasmaBall>create(SpawnGroup.MISC, PlasmaBall::new)
					.dimensions(EntityDimensions.fixed(0.3f, 0.3f))
					.trackRangeBlocks(128).trackedUpdateRate(10)
					.build());
	public static final EntityType<Bullet> BULLET = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier("mcuake", "bullet"),
			FabricEntityTypeBuilder.<Bullet>create(SpawnGroup.MISC, Bullet::new)
					.dimensions(EntityDimensions.fixed(0.05f, 0.05f))
					.trackRangeBlocks(128).trackedUpdateRate(10)
					.build());

	public static final ItemGroup MCUAKE_GROUP = FabricItemGroup.builder(new Identifier("mcuake", "player_settings"))
			.icon(() -> new ItemStack(PLAYER_SETTINGS))
			.build();

	public static final DefaultParticleType NO_PARTICLE = FabricParticleTypes.simple();

	static {
		Registry.register(Registries.SCREEN_HANDLER, new Identifier("mcuake", "jumppad_power"), JUMPPAD_SCREEN_HANDLER);
	}

	@Override
	public void onInitialize() {
		loadItem(PLAYER_SETTINGS, new Identifier("mcuake", "player_settings"));
		loadItem(TOOL, new Identifier("mcuake", "tool"));
		loadItem(MACHINEGUN, new Identifier("mcuake", "machinegun"));
		loadItem(PLASMAGUN, new Identifier("mcuake", "plasmagun"));
		loadItem(RAILGUN, new Identifier("mcuake", "railgun"));

		loadDefaultBlock(SPIKES_BLOCK, new Identifier("mcuake", "spikes"));
		loadDefaultBlock(JUMPPAD_BLOCK, new Identifier("mcuake", "jumppad"));

		Sounds.loadSounds();
		MusicDiscRegistry.LoadDiscs();

		CSMessages.registerServerPackets();
	}

	// Load a block into the block registry and create a default item for it
	private void loadDefaultBlock(Block block, Identifier id) {
		BlockItem blockItem = new BlockItem(block, new Item.Settings());
		Registry.register(Registries.BLOCK, id, block);
		loadItem(blockItem, id);
	}

	// Load an item into the item registry and add it to the MCuake creative tab
	private void loadItem(Item item, Identifier id) {
		Registry.register(Registries.ITEM, id, item);
		ItemGroupEvents.modifyEntriesEvent(MCUAKE_GROUP).register(content -> {
			content.add(item);
		});
	}

}