package com.bytemaniak.mcquake3.registry;

import com.bytemaniak.mcquake3.blocks.Jumppad;
import com.bytemaniak.mcquake3.blocks.JumppadEntity;
import com.bytemaniak.mcquake3.blocks.Spikes;
import com.bytemaniak.mcquake3.blocks.ammo.*;
import com.bytemaniak.mcquake3.blocks.health.*;
import com.bytemaniak.mcquake3.blocks.powerup.*;
import com.bytemaniak.mcquake3.blocks.shield.*;
import com.bytemaniak.mcquake3.blocks.weapon.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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

    public static final Identifier HASTE = new Identifier("mcquake3:haste");
    public static final Block HASTE_BLOCK = new Haste();
    public static final BlockEntityType<HasteEntity> HASTE_ENTITY;

    public static final Identifier INVISIBILITY = new Identifier("mcquake3:invisibility");
    public static final Block INVISIBILITY_BLOCK = new Invisibility();
    public static final BlockEntityType<InvisibilityEntity> INVISIBILITY_ENTITY;

    public static final Identifier QUAD_DAMAGE = new Identifier("mcquake3:quad_damage");
    public static final Block QUAD_DAMAGE_BLOCK = new QuadDamage();
    public static final BlockEntityType<QuadDamageEntity> QUAD_DAMAGE_ENTITY;

    public static final Identifier HEALTH5 = new Identifier("mcquake3:5health");
    public static final Block HEALTH5_BLOCK = new Health5();
    public static final BlockEntityType<Health5Entity> HEALTH5_ENTITY;

    public static final Identifier HEALTH25 = new Identifier("mcquake3:25health");
    public static final Block HEALTH25_BLOCK = new Health25();
    public static final BlockEntityType<Health25Entity> HEALTH25_ENTITY;

    public static final Identifier HEALTH50 = new Identifier("mcquake3:50health");
    public static final Block HEALTH50_BLOCK = new Health50();
    public static final BlockEntityType<Health50Entity> HEALTH50_ENTITY;

    public static final Identifier SHIELD_CELL = new Identifier("mcquake3:energy_shield_cell");
    public static final Block SHIELD_CELL_BLOCK = new ShieldCell();
    public static final BlockEntityType<ShieldCellEntity> SHIELD_CELL_ENTITY;

    public static final Identifier LIGHT_ENERGY_SHIELD = new Identifier("mcquake3:light_energy_shield");
    public static final Block LIGHT_ENERGY_SHIELD_BLOCK = new LightEnergyShield();
    public static final BlockEntityType<LightEnergyShieldEntity> LIGHT_ENERGY_SHIELD_ENTITY;

    public static final Identifier HEAVY_ENERGY_SHIELD = new Identifier("mcquake3:heavy_energy_shield");
    public static final Block HEAVY_ENERGY_SHIELD_BLOCK = new HeavyEnergyShield();
    public static final BlockEntityType<HeavyEnergyShieldEntity> HEAVY_ENERGY_SHIELD_ENTITY;

    public static final Identifier MACHINEGUN = new Identifier("mcquake3:machinegun_pickup");
	public static final Block MACHINEGUN_BLOCK = new MachinegunPickup();
	public static final BlockEntityType<MachinegunPickupEntity> MACHINEGUN_ENTITY;

	public static final Identifier SHOTGUN = new Identifier("mcquake3:shotgun_pickup");
	public static final Block SHOTGUN_BLOCK = new ShotgunPickup();
	public static final BlockEntityType<ShotgunPickupEntity> SHOTGUN_ENTITY;

	public static final Identifier GRENADE = new Identifier("mcquake3:grenade_pickup");
	public static final Block GRENADE_BLOCK = new GrenadePickup();
	public static final BlockEntityType<GrenadePickupEntity> GRENADE_ENTITY;

	public static final Identifier ROCKET = new Identifier("mcquake3:rocket_pickup");
	public static final Block ROCKET_BLOCK = new RocketPickup();
	public static final BlockEntityType<RocketPickupEntity> ROCKET_ENTITY;

	public static final Identifier LIGHTNING = new Identifier("mcquake3:lightning_pickup");
	public static final Block LIGHTNING_BLOCK = new LightningPickup();
	public static final BlockEntityType<LightningPickupEntity> LIGHTNING_ENTITY;

	public static final Identifier RAILGUN = new Identifier("mcquake3:railgun_pickup");
	public static final Block RAILGUN_BLOCK = new RailgunPickup();
	public static final BlockEntityType<RailgunPickupEntity> RAILGUN_ENTITY;

	public static final Identifier PLASMAGUN = new Identifier("mcquake3:plasmagun_pickup");
	public static final Block PLASMAGUN_BLOCK = new PlasmagunPickup();
	public static final BlockEntityType<PlasmagunPickupEntity> PLASMAGUN_ENTITY;

	public static final Identifier BFG = new Identifier("mcquake3:bfg_pickup");
	public static final Block BFG_BLOCK = new BFGPickup();
	public static final BlockEntityType<BFGPickupEntity> BFG_ENTITY;

    public static final Block SPIKES_BLOCK = new Spikes();

    public static final RegistryKey<ItemGroup> MCQUAKE3_BLOCKS_GROUP =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier("mcquake3:mcquake3_blocks"));

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
        HASTE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, HASTE,
                FabricBlockEntityTypeBuilder.create(HasteEntity::new, HASTE_BLOCK).build());
        INVISIBILITY_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, INVISIBILITY,
                FabricBlockEntityTypeBuilder.create(InvisibilityEntity::new, INVISIBILITY_BLOCK).build());
        QUAD_DAMAGE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, QUAD_DAMAGE,
                FabricBlockEntityTypeBuilder.create(QuadDamageEntity::new, QUAD_DAMAGE_BLOCK).build());
        HEALTH5_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, HEALTH5,
                FabricBlockEntityTypeBuilder.create(Health5Entity::new, HEALTH5_BLOCK).build());
        HEALTH25_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, HEALTH25,
                FabricBlockEntityTypeBuilder.create(Health25Entity::new, HEALTH25_BLOCK).build());
        HEALTH50_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, HEALTH50,
                FabricBlockEntityTypeBuilder.create(Health50Entity::new, HEALTH50_BLOCK).build());
        SHIELD_CELL_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, SHIELD_CELL,
                FabricBlockEntityTypeBuilder.create(ShieldCellEntity::new, SHIELD_CELL_BLOCK).build());
        LIGHT_ENERGY_SHIELD_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, LIGHT_ENERGY_SHIELD,
                FabricBlockEntityTypeBuilder.create(LightEnergyShieldEntity::new, LIGHT_ENERGY_SHIELD_BLOCK).build());
        HEAVY_ENERGY_SHIELD_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, HEAVY_ENERGY_SHIELD,
                FabricBlockEntityTypeBuilder.create(HeavyEnergyShieldEntity::new, HEAVY_ENERGY_SHIELD_BLOCK).build());
        MACHINEGUN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, MACHINEGUN,
				FabricBlockEntityTypeBuilder.create(MachinegunPickupEntity::new, MACHINEGUN_BLOCK).build());
		SHOTGUN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, SHOTGUN,
				FabricBlockEntityTypeBuilder.create(ShotgunPickupEntity::new, SHOTGUN_BLOCK).build());
		GRENADE_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, GRENADE,
				FabricBlockEntityTypeBuilder.create(GrenadePickupEntity::new, GRENADE_BLOCK).build());
		ROCKET_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, ROCKET,
				FabricBlockEntityTypeBuilder.create(RocketPickupEntity::new, ROCKET_BLOCK).build());
		LIGHTNING_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, LIGHTNING,
				FabricBlockEntityTypeBuilder.create(LightningPickupEntity::new, LIGHTNING_BLOCK).build());
		RAILGUN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, RAILGUN,
				FabricBlockEntityTypeBuilder.create(RailgunPickupEntity::new, RAILGUN_BLOCK).build());
		PLASMAGUN_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, PLASMAGUN,
				FabricBlockEntityTypeBuilder.create(PlasmagunPickupEntity::new, PLASMAGUN_BLOCK).build());
		BFG_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, BFG,
				FabricBlockEntityTypeBuilder.create(BFGPickupEntity::new, BFG_BLOCK).build());
    }

    public static void loadBlocks() {
        Registry.register(Registries.ITEM_GROUP, MCQUAKE3_BLOCKS_GROUP,
                FabricItemGroup.builder().icon(() -> new ItemStack(JUMPPAD_BLOCK))
                        .displayName(Text.translatable("itemGroup.mcquake3.mcquake3_blocks")).build());

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
        loadDefaultBlock(HASTE_BLOCK, HASTE);
        loadDefaultBlock(INVISIBILITY_BLOCK, INVISIBILITY);
        loadDefaultBlock(QUAD_DAMAGE_BLOCK, QUAD_DAMAGE);
        loadDefaultBlock(HEALTH5_BLOCK, HEALTH5);
        loadDefaultBlock(HEALTH25_BLOCK, HEALTH25);
        loadDefaultBlock(HEALTH50_BLOCK, HEALTH50);
        loadDefaultBlock(SHIELD_CELL_BLOCK, SHIELD_CELL);
        loadDefaultBlock(LIGHT_ENERGY_SHIELD_BLOCK, LIGHT_ENERGY_SHIELD);
        loadDefaultBlock(HEAVY_ENERGY_SHIELD_BLOCK, HEAVY_ENERGY_SHIELD);
        loadDefaultBlock(MACHINEGUN_BLOCK, MACHINEGUN);
        loadDefaultBlock(SHOTGUN_BLOCK, SHOTGUN);
        loadDefaultBlock(GRENADE_BLOCK, GRENADE);
        loadDefaultBlock(ROCKET_BLOCK, ROCKET);
        loadDefaultBlock(LIGHTNING_BLOCK, LIGHTNING);
        loadDefaultBlock(RAILGUN_BLOCK, RAILGUN);
        loadDefaultBlock(PLASMAGUN_BLOCK, PLASMAGUN);
        loadDefaultBlock(BFG_BLOCK, BFG);

    }

    // Load a block into the block registry and create a default item for it
    private static void loadDefaultBlock(Block block, Identifier id) {
        BlockItem blockItem = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, blockItem);
        ItemGroupEvents.modifyEntriesEvent(MCQUAKE3_BLOCKS_GROUP).register(content -> content.add(blockItem));
    }
}
