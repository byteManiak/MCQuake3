package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.blocks.Jumppad;
import com.bytemaniak.mcquake3.blocks.JumppadEntity;
import com.bytemaniak.mcquake3.blocks.Spikes;
import com.bytemaniak.mcquake3.blocks.ammo.*;
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

    private static final Identifier MACHINEGUN_AMMO_BOX = new Identifier("mcquake3", "machinegun_ammo_box");
    public static final Block MACHINEGUN_AMMO_BOX_BLOCK = new MachinegunAmmoBox();
    public static final BlockEntityType<MachinegunAmmoBoxEntity> MACHINEGUN_AMMO_BOX_ENTITY;

    private static final Identifier SHOTGUN_AMMO_BOX = new Identifier("mcquake3", "shotgun_ammo_box");
    public static final Block SHOTGUN_AMMO_BOX_BLOCK = new ShotgunAmmoBox();
    public static final BlockEntityType<ShotgunAmmoBoxEntity> SHOTGUN_AMMO_BOX_ENTITY;

    private static final Identifier GRENADE_AMMO_BOX = new Identifier("mcquake3", "grenade_ammo_box");
    public static final Block GRENADE_AMMO_BOX_BLOCK = new GrenadeAmmoBox();
    public static final BlockEntityType<GrenadeAmmoBoxEntity> GRENADE_AMMO_BOX_ENTITY;

    private static final Identifier ROCKET_AMMO_BOX = new Identifier("mcquake3", "rocket_ammo_box");
    public static final Block ROCKET_AMMO_BOX_BLOCK = new RocketAmmoBox();
    public static final BlockEntityType<RocketAmmoBoxEntity> ROCKET_AMMO_BOX_ENTITY;

    private static final Identifier LIGHTNING_AMMO_BOX = new Identifier("mcquake3", "lightning_ammo_box");
    public static final Block LIGHTNING_AMMO_BOX_BLOCK = new LightningAmmoBox();
    public static final BlockEntityType<LightningAmmoBoxEntity> LIGHTNING_AMMO_BOX_ENTITY;

    private static final Identifier RAILGUN_AMMO_BOX = new Identifier("mcquake3", "railgun_ammo_box");
    public static final Block RAILGUN_AMMO_BOX_BLOCK = new RailgunAmmoBox();
    public static final BlockEntityType<RailgunAmmoBoxEntity> RAILGUN_AMMO_BOX_ENTITY;

    private static final Identifier PLASMAGUN_AMMO_BOX = new Identifier("mcquake3", "plasmagun_ammo_box");
    public static final Block PLASMAGUN_AMMO_BOX_BLOCK = new PlasmagunAmmoBox();
    public static final BlockEntityType<PlasmagunAmmoBoxEntity> PLASMAGUN_AMMO_BOX_ENTITY;

    private static final Identifier BFG_AMMO_BOX = new Identifier("mcquake3", "bfg_ammo_box");
    public static final Block BFG_AMMO_BOX_BLOCK = new BFGAmmoBox();
    public static final BlockEntityType<BFGAmmoBoxEntity> BFG_AMMO_BOX_ENTITY;

    public static final Block SPIKES_BLOCK = new Spikes();

    static {
        JUMPPAD_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, JUMPPAD,
                FabricBlockEntityTypeBuilder.create(JumppadEntity::new, JUMPPAD_BLOCK).build());
        MACHINEGUN_AMMO_BOX_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, MACHINEGUN_AMMO_BOX,
                FabricBlockEntityTypeBuilder.create(MachinegunAmmoBoxEntity::new, MACHINEGUN_AMMO_BOX_BLOCK).build());
        SHOTGUN_AMMO_BOX_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, SHOTGUN_AMMO_BOX,
                FabricBlockEntityTypeBuilder.create(ShotgunAmmoBoxEntity::new, SHOTGUN_AMMO_BOX_BLOCK).build());
        GRENADE_AMMO_BOX_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, GRENADE_AMMO_BOX,
                FabricBlockEntityTypeBuilder.create(GrenadeAmmoBoxEntity::new, GRENADE_AMMO_BOX_BLOCK).build());
        ROCKET_AMMO_BOX_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, ROCKET_AMMO_BOX,
                FabricBlockEntityTypeBuilder.create(RocketAmmoBoxEntity::new, ROCKET_AMMO_BOX_BLOCK).build());
        LIGHTNING_AMMO_BOX_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, LIGHTNING_AMMO_BOX,
                FabricBlockEntityTypeBuilder.create(LightningAmmoBoxEntity::new, LIGHTNING_AMMO_BOX_BLOCK).build());
        RAILGUN_AMMO_BOX_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, RAILGUN_AMMO_BOX,
                FabricBlockEntityTypeBuilder.create(RailgunAmmoBoxEntity::new, RAILGUN_AMMO_BOX_BLOCK).build());
        PLASMAGUN_AMMO_BOX_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PLASMAGUN_AMMO_BOX,
                FabricBlockEntityTypeBuilder.create(PlasmagunAmmoBoxEntity::new, PLASMAGUN_AMMO_BOX_BLOCK).build());
        BFG_AMMO_BOX_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, BFG_AMMO_BOX,
                FabricBlockEntityTypeBuilder.create(BFGAmmoBoxEntity::new, BFG_AMMO_BOX_BLOCK).build());
    }

    public static void loadBlocks() {
        loadDefaultBlock(SPIKES_BLOCK, new Identifier("mcquake3", "spikes"));
        loadDefaultBlock(JUMPPAD_BLOCK, new Identifier("mcquake3", "jumppad"));
        loadDefaultBlock(MACHINEGUN_AMMO_BOX_BLOCK, new Identifier("mcquake3", "machinegun_ammo_box"));
        loadDefaultBlock(SHOTGUN_AMMO_BOX_BLOCK, new Identifier("mcquake3", "shotgun_ammo_box"));
        loadDefaultBlock(GRENADE_AMMO_BOX_BLOCK, new Identifier("mcquake3", "grenade_ammo_box"));
        loadDefaultBlock(ROCKET_AMMO_BOX_BLOCK, new Identifier("mcquake3", "rocket_ammo_box"));
        loadDefaultBlock(LIGHTNING_AMMO_BOX_BLOCK, new Identifier("mcquake3", "lightning_ammo_box"));
        loadDefaultBlock(RAILGUN_AMMO_BOX_BLOCK, new Identifier("mcquake3", "railgun_ammo_box"));
        loadDefaultBlock(PLASMAGUN_AMMO_BOX_BLOCK, new Identifier("mcquake3", "plasmagun_ammo_box"));
        loadDefaultBlock(BFG_AMMO_BOX_BLOCK, new Identifier("mcquake3", "bfg_ammo_box"));
    }

    // Load a block into the block registry and create a default item for it
    private static void loadDefaultBlock(Block block, Identifier id) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.BLOCK, id, block);
        loadItem(blockItem, id);
    }
}
