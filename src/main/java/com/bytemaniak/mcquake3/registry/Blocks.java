package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.blocks.Jumppad;
import com.bytemaniak.mcquake3.blocks.JumppadEntity;
import com.bytemaniak.mcquake3.blocks.Spikes;
import com.bytemaniak.mcquake3.blocks.ammo.*;
import com.bytemaniak.mcquake3.blocks.health.*;
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
    private static final Identifier JUMPPAD = new Identifier("mcquake3:jumppad");
    public static final Block JUMPPAD_BLOCK = new Jumppad();
    public static final BlockEntityType<JumppadEntity> JUMPPAD_BLOCK_ENTITY;

    public static final Identifier MACHINEGUN_AMMO_BOX = new Identifier("mcquake3:machinegun_ammo_box");
    public static final Block MACHINEGUN_AMMO_BOX_BLOCK = new MachinegunAmmoBox();
    public static final BlockEntityType<MachinegunAmmoBoxEntity> MACHINEGUN_AMMO_BOX_ENTITY;

    public static final Identifier SHOTGUN_AMMO_BOX = new Identifier("mcquake3:shotgun_ammo_box");
    public static final Block SHOTGUN_AMMO_BOX_BLOCK = new ShotgunAmmoBox();
    public static final BlockEntityType<ShotgunAmmoBoxEntity> SHOTGUN_AMMO_BOX_ENTITY;

    public static final Identifier GRENADE_AMMO_BOX = new Identifier("mcquake3:grenade_ammo_box");
    public static final Block GRENADE_AMMO_BOX_BLOCK = new GrenadeAmmoBox();
    public static final BlockEntityType<GrenadeAmmoBoxEntity> GRENADE_AMMO_BOX_ENTITY;

    public static final Identifier ROCKET_AMMO_BOX = new Identifier("mcquake3:rocket_ammo_box");
    public static final Block ROCKET_AMMO_BOX_BLOCK = new RocketAmmoBox();
    public static final BlockEntityType<RocketAmmoBoxEntity> ROCKET_AMMO_BOX_ENTITY;

    public static final Identifier LIGHTNING_AMMO_BOX = new Identifier("mcquake3:lightning_ammo_box");
    public static final Block LIGHTNING_AMMO_BOX_BLOCK = new LightningAmmoBox();
    public static final BlockEntityType<LightningAmmoBoxEntity> LIGHTNING_AMMO_BOX_ENTITY;

    public static final Identifier RAILGUN_AMMO_BOX = new Identifier("mcquake3:railgun_ammo_box");
    public static final Block RAILGUN_AMMO_BOX_BLOCK = new RailgunAmmoBox();
    public static final BlockEntityType<RailgunAmmoBoxEntity> RAILGUN_AMMO_BOX_ENTITY;

    public static final Identifier PLASMAGUN_AMMO_BOX = new Identifier("mcquake3:plasmagun_ammo_box");
    public static final Block PLASMAGUN_AMMO_BOX_BLOCK = new PlasmagunAmmoBox();
    public static final BlockEntityType<PlasmagunAmmoBoxEntity> PLASMAGUN_AMMO_BOX_ENTITY;

    public static final Identifier BFG_AMMO_BOX = new Identifier("mcquake3:bfg_ammo_box");
    public static final Block BFG_AMMO_BOX_BLOCK = new BFGAmmoBox();
    public static final BlockEntityType<BFGAmmoBoxEntity> BFG_AMMO_BOX_ENTITY;

    public static final Identifier HEALTH5 = new Identifier("mcquake3:5health");
    public static final Block HEALTH5_BLOCK = new Health5();
    public static final BlockEntityType<Health5Entity> HEALTH5_ENTITY;

    public static final Identifier HEALTH25 = new Identifier("mcquake3:25health");
    public static final Block HEALTH25_BLOCK = new Health25();
    public static final BlockEntityType<Health25Entity> HEALTH25_ENTITY;

    public static final Identifier HEALTH50 = new Identifier("mcquake3:50health");
    public static final Block HEALTH50_BLOCK = new Health50();
    public static final BlockEntityType<Health50Entity> HEALTH50_ENTITY;

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
        HEALTH5_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, HEALTH5,
                FabricBlockEntityTypeBuilder.create(Health5Entity::new, HEALTH5_BLOCK).build());
        HEALTH25_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, HEALTH25,
                FabricBlockEntityTypeBuilder.create(Health25Entity::new, HEALTH25_BLOCK).build());
        HEALTH50_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, HEALTH50,
                FabricBlockEntityTypeBuilder.create(Health50Entity::new, HEALTH50_BLOCK).build());
    }

    public static void loadBlocks() {
        loadDefaultBlock(SPIKES_BLOCK, new Identifier("mcquake3:spikes"));
        loadDefaultBlock(JUMPPAD_BLOCK, new Identifier("mcquake3:jumppad"));
        loadDefaultBlock(MACHINEGUN_AMMO_BOX_BLOCK, MACHINEGUN_AMMO_BOX);
        loadDefaultBlock(SHOTGUN_AMMO_BOX_BLOCK, SHOTGUN_AMMO_BOX);
        loadDefaultBlock(GRENADE_AMMO_BOX_BLOCK, GRENADE_AMMO_BOX);
        loadDefaultBlock(ROCKET_AMMO_BOX_BLOCK, ROCKET_AMMO_BOX);
        loadDefaultBlock(LIGHTNING_AMMO_BOX_BLOCK, LIGHTNING_AMMO_BOX);
        loadDefaultBlock(RAILGUN_AMMO_BOX_BLOCK, RAILGUN_AMMO_BOX);
        loadDefaultBlock(PLASMAGUN_AMMO_BOX_BLOCK, PLASMAGUN_AMMO_BOX);
        loadDefaultBlock(BFG_AMMO_BOX_BLOCK, BFG_AMMO_BOX);
        loadDefaultBlock(HEALTH5_BLOCK, HEALTH5);
        loadDefaultBlock(HEALTH25_BLOCK, HEALTH25);
        loadDefaultBlock(HEALTH50_BLOCK, HEALTH50);
    }

    // Load a block into the block registry and create a default item for it
    private static void loadDefaultBlock(Block block, Identifier id) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.BLOCK, id, block);
        loadItem(blockItem, id);
    }
}
