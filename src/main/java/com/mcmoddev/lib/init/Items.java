package com.mcmoddev.lib.init;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mcmoddev.basemetals.BaseMetals;
import com.mcmoddev.lib.util.ConfigBase.Options;
import com.mcmoddev.lib.block.*;
import com.mcmoddev.lib.data.Names;
import com.mcmoddev.lib.item.*;
import com.mcmoddev.lib.material.IMMDObject;
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.material.MMDMaterial.MaterialType;
import com.mcmoddev.lib.util.Oredicts;
import com.mcmoddev.lib.util.TabContainer;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class initializes all items in Base Metals and provides some utility
 * methods for looking up items.
 *
 * @author Jasmine Iwanek
 *
 */
public abstract class Items {

	private static boolean initDone = false;

	private static BiMap<String, Item> itemRegistry = HashBiMap.create(34);
	private static Map<MMDMaterial, List<Item>> itemsByMaterial = new HashMap<>();

	private static EnumMap<Names, Class<? extends Item>> nameToClass = new EnumMap<>(Names.class);
	private static EnumMap<Names, String> nameToOredict = new EnumMap<>(Names.class);
	private static EnumMap<Names, Boolean> nameToEnabled = new EnumMap<>(Names.class);

	private static Map<Class<?>, Integer> classSortingValues = new HashMap<>();
	private static Map<MMDMaterial, Integer> materialSortingValues = new HashMap<>();

	// public static UniversalBucket universal_bucket; // now automatically added by Forge

	protected Items() {
		throw new IllegalAccessError("Not a instantiable class");
	}

	/**
	 *
	 */
	public static void init() {
		if (initDone) {
			return;
		}

		com.mcmoddev.basemetals.util.Config.init();
		Blocks.init();

		mapNameToClass(Names.CRYSTAL, ItemMMDIngot.class);
		mapNameToClass(Names.GEM, ItemMMDIngot.class);
		mapNameToClass(Names.ANVIL, ItemMMDAnvilBlock.class);
		mapNameToClass(Names.ARROW, ItemMMDArrow.class);
		mapNameToClass(Names.AXE, ItemMMDAxe.class);
		mapNameToClass(Names.BLEND, ItemMMDBlend.class);
		mapNameToClass(Names.BOLT, ItemMMDBolt.class);
		mapNameToClass(Names.BOOTS, ItemMMDArmor.class);
		mapNameToClass(Names.BOW, ItemMMDBow.class);
		mapNameToClass(Names.CHESTPLATE, ItemMMDArmor.class);
		mapNameToClass(Names.CRACKHAMMER, ItemMMDCrackHammer.class);
		mapNameToClass(Names.CROSSBOW, ItemMMDCrossbow.class);
		mapNameToClass(Names.DOOR, ItemMMDDoor.class);
		mapNameToClass(Names.FISHING_ROD, ItemMMDFishingRod.class);
		mapNameToClass(Names.HELMET, ItemMMDArmor.class);
		mapNameToClass(Names.HOE, ItemMMDHoe.class);
		mapNameToClass(Names.HORSE_ARMOR, ItemMMDHorseArmor.class);
		mapNameToClass(Names.INGOT, ItemMMDIngot.class);
		mapNameToClass(Names.LEGGINGS, ItemMMDArmor.class);
		mapNameToClass(Names.NUGGET, ItemMMDNugget.class);
		mapNameToClass(Names.PICKAXE, ItemMMDPickaxe.class);
		mapNameToClass(Names.POWDER, ItemMMDPowder.class);
		mapNameToClass(Names.SHEARS, ItemMMDShears.class);
		mapNameToClass(Names.SHIELD, ItemMMDShield.class);
		mapNameToClass(Names.SHOVEL, ItemMMDShovel.class);
		mapNameToClass(Names.SLAB, ItemMMDSlab.class);
		mapNameToClass(Names.SMALLBLEND, ItemMMDSmallBlend.class);
		mapNameToClass(Names.SMALLPOWDER, ItemMMDSmallPowder.class);
		mapNameToClass(Names.SWORD, ItemMMDSword.class);
		mapNameToClass(Names.ROD, ItemMMDRod.class);
		mapNameToClass(Names.GEAR, ItemMMDGear.class);

		mapNameToClass(Names.CASING, GenericMMDItem.class);
		mapNameToClass(Names.DENSE_PLATE, GenericMMDItem.class);

		mapNameToClass(Names.CRUSHED, GenericMMDItem.class);
		mapNameToClass(Names.CRUSHED_PURIFIED, GenericMMDItem.class);

//		mapNameToClass(Names.CRYSTAL, GenericMMDItem.class);
		mapNameToClass(Names.SHARD, GenericMMDItem.class);
		mapNameToClass(Names.CLUMP, GenericMMDItem.class);
		mapNameToClass(Names.POWDER_DIRTY, GenericMMDItem.class);

		final String armor = "Armor";
		final String basics = "Basics";
		final String basicTools = "BasicTools";
		final String smallDust = "SmallDust";
		final String crossbowAndBolt = "CrossbowAndBolt";
		final String bowAndArrow = "BowAndArrow";

		mapNameToEnabled(Names.CRYSTAL, Options.isThingEnabled(basics));
		mapNameToEnabled(Names.GEM, Options.isThingEnabled(basics));
		mapNameToEnabled(Names.ANVIL, Options.isThingEnabled("Anvil"));
		mapNameToEnabled(Names.ARROW, Options.isThingEnabled(bowAndArrow));
		mapNameToEnabled(Names.AXE, Options.isThingEnabled(basicTools));
		mapNameToEnabled(Names.BLEND, Options.isThingEnabled(basics));
		mapNameToEnabled(Names.BOLT, Options.isThingEnabled(crossbowAndBolt));
		mapNameToEnabled(Names.BOOTS, Options.isThingEnabled(armor));
		mapNameToEnabled(Names.BOW, Options.isThingEnabled(bowAndArrow));
		mapNameToEnabled(Names.CHESTPLATE, Options.isThingEnabled(armor));
		mapNameToEnabled(Names.CRACKHAMMER, Options.isThingEnabled("CrackHammer"));
		mapNameToEnabled(Names.CROSSBOW, Options.isThingEnabled(crossbowAndBolt));
		mapNameToEnabled(Names.DOOR, Options.isThingEnabled("Door"));
		mapNameToEnabled(Names.FISHING_ROD, Options.isThingEnabled("FishingRod"));
		mapNameToEnabled(Names.HELMET, Options.isThingEnabled(armor));
		mapNameToEnabled(Names.HOE, Options.isThingEnabled(basicTools));
		mapNameToEnabled(Names.HORSE_ARMOR, Options.isThingEnabled("HorseArmor"));
		mapNameToEnabled(Names.INGOT, Options.isThingEnabled(basics));
		mapNameToEnabled(Names.LEGGINGS, Options.isThingEnabled(armor));
		mapNameToEnabled(Names.NUGGET, Options.isThingEnabled(basics));
		mapNameToEnabled(Names.PICKAXE, Options.isThingEnabled(basicTools));
		mapNameToEnabled(Names.POWDER, Options.isThingEnabled(basics));
		mapNameToEnabled(Names.SHEARS, Options.isThingEnabled("Shears"));
		mapNameToEnabled(Names.SHIELD, Options.isThingEnabled("Shield"));
		mapNameToEnabled(Names.SHOVEL, Options.isThingEnabled(basicTools));
		mapNameToEnabled(Names.SLAB, Options.isThingEnabled("Slab"));
		mapNameToEnabled(Names.SMALLBLEND, Options.isThingEnabled(smallDust));
		mapNameToEnabled(Names.SMALLPOWDER, Options.isThingEnabled(smallDust));
		mapNameToEnabled(Names.SWORD, Options.isThingEnabled(basicTools));
		mapNameToEnabled(Names.ROD, Options.isThingEnabled("Rod"));
		mapNameToEnabled(Names.GEAR, Options.isThingEnabled("Gear"));

		mapNameToEnabled(Names.CASING, Options.enableModderSupportThings());
		mapNameToEnabled(Names.DENSE_PLATE, Options.enableModderSupportThings());

		final String ic2 = "ic2";
//		mapNameToEnabled(Names.CASING, Options.modEnabled(ic2));
//		mapNameToEnabled(Names.DENSE_PLATE, Options.modEnabled(ic2));

		mapNameToEnabled(Names.CRUSHED, Options.isModEnabled(ic2));
		mapNameToEnabled(Names.CRUSHED_PURIFIED, Options.isModEnabled(ic2));

		final String mekanism = "mekanism";
//		mapNameToEnabled(Names.CRYSTAL, Options.modEnabled(mekanism));
		mapNameToEnabled(Names.SHARD, Options.isModEnabled(mekanism));
		mapNameToEnabled(Names.CLUMP, Options.isModEnabled(mekanism));
		mapNameToEnabled(Names.POWDER_DIRTY, Options.isModEnabled(mekanism));

		mapNameToOredict(Names.CRYSTAL, Oredicts.CRYSTAL);
		mapNameToOredict(Names.GEM, Oredicts.GEM);
		mapNameToOredict(Names.ANVIL, null);
		mapNameToOredict(Names.ARROW, Oredicts.ARROW);
		mapNameToOredict(Names.AXE, null);
		mapNameToOredict(Names.BLEND, Oredicts.DUST);
		mapNameToOredict(Names.BOLT, null);
		mapNameToOredict(Names.BOOTS, null);
		mapNameToOredict(Names.BOW, null);
		mapNameToOredict(Names.CHESTPLATE, null);
		mapNameToOredict(Names.CRACKHAMMER, null);
		mapNameToOredict(Names.CROSSBOW, null);
		mapNameToOredict(Names.DOOR, null);
		mapNameToOredict(Names.FISHING_ROD, null);
		mapNameToOredict(Names.HELMET, null);
		mapNameToOredict(Names.HOE, null);
		mapNameToOredict(Names.HORSE_ARMOR, null);
		mapNameToOredict(Names.INGOT, Oredicts.INGOT);
		mapNameToOredict(Names.LEGGINGS, null);
		mapNameToOredict(Names.NUGGET, Oredicts.NUGGET);
		mapNameToOredict(Names.PICKAXE, null);
		mapNameToOredict(Names.POWDER, Oredicts.DUST);
		mapNameToOredict(Names.SHEARS, null);
		mapNameToOredict(Names.SHIELD, Oredicts.SHIELD);
		mapNameToOredict(Names.SHOVEL, null);
		mapNameToOredict(Names.SLAB, Oredicts.SLAB);
		mapNameToOredict(Names.SMALLBLEND, Oredicts.DUST_TINY);
		mapNameToOredict(Names.SMALLPOWDER, Oredicts.DUST_TINY);
		mapNameToOredict(Names.SWORD, null);
		mapNameToOredict(Names.ROD, Oredicts.ROD);
		mapNameToOredict(Names.GEAR, Oredicts.GEAR);

		mapNameToOredict(Names.CASING, Oredicts.CASING);
		mapNameToOredict(Names.DENSE_PLATE, Oredicts.PLATE_DENSE);

		mapNameToOredict(Names.CRUSHED, Oredicts.CRUSHED);
		mapNameToOredict(Names.CRUSHED_PURIFIED, Oredicts.CRUSHED_PURIFIED);

//		mapNameToOredict(Names.CRYSTAL, Oredicts.CRUSHED);
		mapNameToOredict(Names.SHARD, Oredicts.SHARD);
		mapNameToOredict(Names.CLUMP, Oredicts.CLUMP);
		mapNameToOredict(Names.POWDER_DIRTY, Oredicts.DUST_DIRTY);

		try {
			expandCombatArrays(net.minecraft.item.ItemAxe.class);
		} catch (IllegalAccessException | NoSuchFieldException ex) {
			BaseMetals.logger.error("Error modifying item classes", ex);
		}

		setSortingList();
		addToMetList();

		initDone = true;
	}

	private static void setSortingList() {
		int ss = 0;
		classSortingValues.put(BlockMMDOre.class, ++ss * 10000);
		classSortingValues.put(BlockMMDBlock.class, ++ss * 10000);
		classSortingValues.put(BlockMMDPlate.class, ++ss * 10000);
		classSortingValues.put(BlockMMDBars.class, ++ss * 10000);
		classSortingValues.put(BlockMMDDoor.class, ++ss * 10000);
		classSortingValues.put(BlockMMDTrapDoor.class, ++ss * 10000);
		classSortingValues.put(InteractiveFluidBlock.class, ++ss * 10000);
		classSortingValues.put(BlockMoltenFluid.class, ++ss * 10000);
		classSortingValues.put(ItemMMDIngot.class, ++ss * 10000);
		classSortingValues.put(ItemMMDNugget.class, ++ss * 10000);
		classSortingValues.put(ItemMMDPowder.class, ++ss * 10000);
		classSortingValues.put(ItemMMDBlend.class, classSortingValues.get(ItemMMDPowder.class));
		classSortingValues.put(ItemMMDSmallPowder.class, ++ss * 10000);
		classSortingValues.put(ItemMMDSmallBlend.class, classSortingValues.get(ItemMMDSmallPowder.class));
		classSortingValues.put(ItemMMDCrackHammer.class, ++ss * 10000);
		classSortingValues.put(ItemMMDPickaxe.class, ++ss * 10000);
		classSortingValues.put(ItemMMDShovel.class, ++ss * 10000);
		classSortingValues.put(ItemMMDAxe.class, ++ss * 10000);
		classSortingValues.put(ItemMMDHoe.class, ++ss * 10000);
		classSortingValues.put(ItemMMDSword.class, ++ss * 10000);
		classSortingValues.put(ItemMMDArmor.class, ++ss * 10000);
		classSortingValues.put(ItemMMDArrow.class, ++ss * 10000);
		classSortingValues.put(ItemMMDBolt.class, ++ss * 10000);
		classSortingValues.put(ItemMMDBow.class, ++ss * 10000);
		classSortingValues.put(ItemMMDCrossbow.class, ++ss * 10000);
		classSortingValues.put(ItemMMDFishingRod.class, ++ss * 10000);
		classSortingValues.put(ItemMMDHorseArmor.class, ++ss * 10000);
		classSortingValues.put(ItemMMDShears.class, ++ss * 10000);
		classSortingValues.put(ItemMMDShield.class, ++ss * 10000);
		classSortingValues.put(ItemMMDGear.class, ++ss * 10000);
		classSortingValues.put(ItemMMDRod.class, ++ss * 10000);
		classSortingValues.put(ItemMMDDoor.class, classSortingValues.get(BlockMMDDoor.class));
		classSortingValues.put(GenericMMDItem.class, ++ss * 10000);

		classSortingValues.put(BlockMMDButton.class, ++ss * 10000);
		classSortingValues.put(BlockMMDSlab.class, ++ss * 10000);
		classSortingValues.put(BlockMMDDoubleSlab.class, ++ss * 10000);
		classSortingValues.put(BlockMMDHalfSlab.class, ++ss * 10000);
		classSortingValues.put(BlockMMDLever.class, ++ss * 10000);
		classSortingValues.put(BlockMMDPressurePlate.class, ++ss * 10000);
		classSortingValues.put(BlockMMDStairs.class, ++ss * 10000);
		classSortingValues.put(BlockMMDWall.class, ++ss * 10000);
		classSortingValues.put(BlockMoltenFluid.class, ++ss * 10000);
		classSortingValues.put(ItemMMDSlab.class, classSortingValues.get(BlockMMDSlab.class));
	}

	protected static void addToMetList() {
		final List<MMDMaterial> metlist = new ArrayList<>(Materials.getAllMaterials().size());
		metlist.addAll(Materials.getAllMaterials());
		metlist.sort((MMDMaterial a, MMDMaterial b) -> a.getName().compareToIgnoreCase(b.getName()));
		for (int i = 0; i < metlist.size(); i++) {
			materialSortingValues.put(metlist.get(i), i * 100);
		}
	}

	protected static void createItemsBasic(@Nonnull final String materialName, @Nonnull final TabContainer tabs) {
		createItemsBasic(Materials.getMaterialByName(materialName), tabs);
	}
	/**
	 * 
	 * @param material
	 *            The material base of these items
	 * @param tabs
	 *            TabContainer covering the various CreativeTabs items might be
	 *            on
	 */
	protected static void createItemsBasic(@Nonnull final MMDMaterial material, @Nonnull final TabContainer tabs) {
		create(Names.BLEND, material, tabs.itemsTab);
		create(Names.INGOT, material, tabs.itemsTab);
		create(Names.NUGGET, material, tabs.itemsTab);
		create(Names.POWDER, material, tabs.itemsTab);
		create(Names.SMALLBLEND, material, tabs.itemsTab);
		create(Names.SMALLPOWDER, material, tabs.itemsTab);
	}

	protected static void createItemsAdditional(@Nonnull final String materialName, @Nonnull final TabContainer tabs) {
		createItemsAdditional(Materials.getMaterialByName(materialName), tabs);
	}

	protected static void createItemsAdditional(@Nonnull final MMDMaterial material, @Nonnull final TabContainer tabs) {
		create(Names.ARROW, material, tabs.toolsTab);
		create(Names.AXE, material, tabs.toolsTab);
		create(Names.BOLT, material, tabs.toolsTab);
		create(Names.BOOTS, material, tabs.itemsTab);
		create(Names.BOW, material, tabs.toolsTab);
		create(Names.CHESTPLATE, material, tabs.itemsTab);
		create(Names.CRACKHAMMER, material, tabs.toolsTab);
		create(Names.CROSSBOW, material, tabs.toolsTab);
		create(Names.DOOR, material, tabs.blocksTab);
		create(Names.FISHING_ROD, material, tabs.toolsTab);
		create(Names.HELMET, material, tabs.toolsTab);
		create(Names.HOE, material, tabs.toolsTab);
		create(Names.HORSE_ARMOR, material, tabs.itemsTab);
		create(Names.LEGGINGS, material, tabs.itemsTab);
		create(Names.PICKAXE, material, tabs.toolsTab);
		create(Names.SHEARS, material, tabs.toolsTab);
		create(Names.SHIELD, material, tabs.itemsTab);
		create(Names.SHOVEL, material, tabs.toolsTab);
		create(Names.SLAB, material, tabs.blocksTab);
		create(Names.SWORD, material, tabs.itemsTab);
		create(Names.ROD, material, tabs.itemsTab);
		create(Names.GEAR, material, tabs.itemsTab);
	}

	protected static void createItemsFull(@Nonnull final String materialName, @Nonnull final TabContainer tabs) {
		createItemsFull(Materials.getMaterialByName(materialName), tabs);
	}

	/**
	 * 
	 * @param material
	 *            The material base of these items
	 * @param tabs
	 *            TabContainer covering the various CreativeTabs items might be
	 *            on
	 */
	protected static void createItemsFull(@Nonnull final MMDMaterial material, @Nonnull final TabContainer tabs) {
		createItemsBasic(material, tabs);
		createItemsAdditional(material, tabs);
	}

	protected static void createItemsModSupport(@Nonnull final String materialName, @Nonnull final TabContainer tabs) {
		createItemsModSupport(Materials.getMaterialByName(materialName), tabs);
	}

	/**
	 * 
	 * @param material
	 *            The material base of these items
	 * @param tabs
	 *            TabContainer covering the various CreativeTabs items might be
	 *            on
	 */
	protected static void createItemsModSupport(@Nonnull final MMDMaterial material, @Nonnull final TabContainer tabs) {
		if (Options.enableModderSupportThings()) {
			create(Names.CASING, material, tabs.itemsTab);
			create(Names.DENSE_PLATE, material, tabs.itemsTab);
		}

		createItemsModMekanism(material, tabs);
		createItemsModIC2(material, tabs);
	}

	protected static void createItemsModIC2(@Nonnull final String materialName, @Nonnull final TabContainer tabs) {
		createItemsModIC2(Materials.getMaterialByName(materialName), tabs);
	}

	/**
	 * 
	 * @param material
	 *            The material base of these items
	 * @param tabs
	 *            TabContainer covering the various CreativeTabs items might be
	 *            on
	 */
	protected static void createItemsModIC2(@Nonnull final MMDMaterial material, @Nonnull final TabContainer tabs) {

		if (material.hasOre()) {
			create(Names.CRUSHED, material, tabs.itemsTab);
			create(Names.CRUSHED_PURIFIED, material, tabs.itemsTab);
		}
	}

	protected static void createItemsModMekanism(@Nonnull final String materialName, @Nonnull final TabContainer tabs) {
		createItemsModMekanism(Materials.getMaterialByName(materialName), tabs);
	}

	/**
	 * 
	 * @param material
	 *            The material base of these items
	 * @param tabs
	 *            TabContainer covering the various CreativeTabs items might be
	 *            on
	 */
	protected static void createItemsModMekanism(@Nonnull final MMDMaterial material, @Nonnull final TabContainer tabs) {
		if (material.hasOre()) {
			createMekCrystal(material, tabs.itemsTab);
			create(Names.SHARD, material, tabs.itemsTab);
			create(Names.CLUMP, material, tabs.itemsTab);
			create(Names.POWDER_DIRTY, material, tabs.itemsTab);
		}
	}

	protected static Item create(@Nonnull final Names name, @Nonnull final String materialName, final CreativeTabs tab) {
		return create(name, Materials.getMaterialByName(materialName), tab);
	}

	/**
	 * 
	 * @param name
	 *            Name of the requested item type
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Item create(@Nonnull final Names name, @Nonnull final MMDMaterial material, final CreativeTabs tab) {
		if ((material == null) || (name == null)) {
			return null;
		}

		if (material.hasItem(name)) {
			return material.getItem(name);
		}

		if ((name.equals(Names.HELMET)) || (name.equals(Names.CHESTPLATE)) || (name.equals(Names.LEGGINGS)) || (name.equals(Names.BOOTS))) {
			return createArmorItem(name, material, tab);
		}

		if ((((name.equals(Names.BLEND)) || name.equals(Names.SMALLBLEND)) && (!material.hasBlend()))
				|| (name.equals(Names.ANVIL) && (!material.hasBlock(Names.ANVIL)))
				|| (name.equals(Names.DOOR) && (!material.hasBlock(Names.DOOR)))
				|| (name.equals(Names.SLAB) && (!material.hasBlock(Names.SLAB) && (!material.hasBlock(Names.DOUBLE_SLAB))))) {
			return null;				
		}

		final Item item = createItem(material, name.toString(), getClassFromName(name), isNameEnabled(name), tab);

		final String oredict = getOredictFromName(name);
		if (item != null) {
			if (oredict != null) {
				Oredicts.registerOre(oredict + material.getCapitalizedName(), item);
				if (name.equals(Names.ROD)) {
					Oredicts.registerOre(Oredicts.STICK + material.getCapitalizedName(), item);
					Oredicts.registerOre(Oredicts.ROD, item);
				} else if (name.equals(Names.GEAR)) {
					Oredicts.registerOre(Oredicts.GEAR, item);
				}
			}
			if (name.equals(Names.DOOR)) {
				Oredicts.registerOre(Oredicts.DOOR, item);
			}
		}

		return null;
	}

	protected static Item addItem(@Nonnull final Item item, @Nonnull final Names name, final CreativeTabs tab) {
		return addItem(item, name.toString(), null, tab);
	}

	protected static Item addItem(@Nonnull final Item item, @Nonnull final String name, final CreativeTabs tab) {
		return addItem(item, name, null, tab);
	}

	/**
	 * 
	 * @param item
	 *            The item to add
	 * @param name
	 *            Name of the item
	 * @param material
	 *            Material it is made from
	 * @param tab
	 *            which creative tab it is in
	 * @return the item that was added
	 */
	protected static Item addItem(@Nonnull final Item item, @Nonnull final String name, final MMDMaterial material, final CreativeTabs tab) {
		String fullName;
		if (material != null) {
			if (material.hasItem(name)) {
				return material.getItem(name);
			}
			fullName = material.getName() + "_" + name;
		} else {
			fullName = name;
		}

		item.setRegistryName(fullName);
		item.setUnlocalizedName(item.getRegistryName().getResourceDomain() + "." + fullName);
		GameRegistry.register(item);
		itemRegistry.put(fullName, item);

		if (tab != null) {
			item.setCreativeTab(tab);
		}

		if (material != null) {
			itemsByMaterial.computeIfAbsent(material, (MMDMaterial g) -> new ArrayList<>());
			itemsByMaterial.get(material).add(item);
		}

		return item;
	}

	private static Item createItem(@Nonnull final MMDMaterial material, @Nonnull final String name, @Nonnull final Class<? extends Item> clazz, @Nonnull final boolean enabled, final CreativeTabs tab) {
		if (material.hasItem(name)) {
			return material.getItem(name);
		}

		if (enabled) {
			Constructor<?> ctor = null;
			Item inst = null;

			try {
				ctor = clazz.getConstructor(material.getClass());
			} catch (NoSuchMethodException|SecurityException ex) {
				BaseMetals.logger.error("Class for Item named " + name + " does not have the correct constructor", ex);
				return null;
			}

			try {
				inst = (Item) ctor.newInstance(material);
			} catch (IllegalAccessException|IllegalArgumentException|InstantiationException|InvocationTargetException|ExceptionInInitializerError ex) {
				BaseMetals.logger.error("Unable to create new instance of Item class for item name " + name + " of material " + material.getCapitalizedName(), ex);
				return null;
			} catch (Exception ex) {
				BaseMetals.logger.error("Unable to create Item named " + name + " for material " + material.getCapitalizedName(), ex);
				return null;
			}

			if (inst != null) {
				addItem(inst, name, material, tab);
				material.addNewItem(name, inst);
				return inst;
			}
		}
		return null;
	}

	private static Item createArmorItem(@Nonnull final Names name, @Nonnull final MMDMaterial material, final CreativeTabs tab) {
		if (!(isNameEnabled(name))) {
			return null;
		}

		if (material.hasItem(name)) {
			return material.getItem(name);
		}

		Item item = null;
		switch (name) {
		case HELMET:
			item = ItemMMDArmor.createHelmet(material);
			break;
		case CHESTPLATE:
			item = ItemMMDArmor.createChestplate(material);
			break;
		case LEGGINGS:
			item = ItemMMDArmor.createLeggings(material);
			break;
		case BOOTS:
			item = ItemMMDArmor.createBoots(material);
			break;
		default:
		}

		if (item == null) {
			return null;
		}
		Item finalItem = addItem(item, name.toString(), material, tab);
		material.addNewItem(name, finalItem);
		return finalItem;
	}

	/**
	 * 
	 * @param material
	 *            The material base of this crystal
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createCrystal(MMDMaterial material, CreativeTabs tab) {
		return create(Names.CRYSTAL, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this gem
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createGem(MMDMaterial material, CreativeTabs tab) {
		return create(Names.GEM, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this ingot
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createIngot(MMDMaterial material, CreativeTabs tab) {
		return create(Names.INGOT, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this nugget
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createNugget(MMDMaterial material, CreativeTabs tab) {
		return create(Names.NUGGET, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this powder
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createPowder(MMDMaterial material, CreativeTabs tab) {
		return create(Names.POWDER, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this blend
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createBlend(MMDMaterial material, CreativeTabs tab) {
		return create(Names.BLEND, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this rod
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createRod(MMDMaterial material, CreativeTabs tab) {
		return create(Names.ROD, material, tab);
	}

	// TODO: Possibly make this a placable Block
	/**
	 * 
	 * @param material
	 *            The material base of this gear
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createGear(MMDMaterial material, CreativeTabs tab) {
		return create(Names.GEAR, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this axe
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createAxe(MMDMaterial material, CreativeTabs tab) {
		return create(Names.AXE, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this crackhammer
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createCrackhammer(MMDMaterial material, CreativeTabs tab) {
		return create(Names.CRACKHAMMER, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createHoe(MMDMaterial material, CreativeTabs tab) {
		return create(Names.HOE, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createPickaxe(MMDMaterial material, CreativeTabs tab) {
		return create(Names.PICKAXE, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createShovel(MMDMaterial material, CreativeTabs tab) {
		return create(Names.SHOVEL, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createSword(MMDMaterial material, CreativeTabs tab) {
		return create(Names.SWORD, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createHelmet(MMDMaterial material, CreativeTabs tab) {
		return create(Names.HELMET, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createChestplate(MMDMaterial material, CreativeTabs tab) {
		return create(Names.CHESTPLATE, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createLeggings(MMDMaterial material, CreativeTabs tab) {
		return create(Names.LEGGINGS, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createBoots(MMDMaterial material, CreativeTabs tab) {
		return create(Names.BOOTS, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createHorseArmor(MMDMaterial material, CreativeTabs tab) {
		return create(Names.HORSE_ARMOR, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createArrow(MMDMaterial material, CreativeTabs tab) {
		return create(Names.ARROW, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createBolt(MMDMaterial material, CreativeTabs tab) {
		return create(Names.BOLT, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createBow(MMDMaterial material, CreativeTabs tab) {
		return create(Names.BOW, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createCrossbow(MMDMaterial material, CreativeTabs tab) {
		return create(Names.CROSSBOW, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createShears(MMDMaterial material, CreativeTabs tab) {
		return create(Names.SHEARS, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createSmallBlend(MMDMaterial material, CreativeTabs tab) {
		return create(Names.SMALLBLEND, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createFishingRod(MMDMaterial material, CreativeTabs tab) {
		return create(Names.FISHING_ROD, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createSmallPowder(MMDMaterial material, CreativeTabs tab) {
		return create(Names.SMALLPOWDER, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createShield(MMDMaterial material, CreativeTabs tab) {
		return create(Names.SHIELD, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createMekCrystal(@Nonnull final MMDMaterial material, final CreativeTabs tab) {
		if (material.hasItem(Names.CRYSTAL)) {
			return material.getItem(Names.CRYSTAL);
		}

		final Item item = createItem(material, Names.CRYSTAL.toString(), GenericMMDItem.class, (Options.isModEnabled("mekanism") && material.getType() != MaterialType.CRYSTAL), tab);
		if (item != null) {
			Oredicts.registerOre(Oredicts.CRYSTAL + material.getCapitalizedName(), item);
			return item;
		}

		return null;
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createMekShard(MMDMaterial material, CreativeTabs tab) {
		return create(Names.SHARD, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createMekClump(MMDMaterial material, CreativeTabs tab) {
		return create(Names.CLUMP, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createMekDirtyPowder(MMDMaterial material, CreativeTabs tab) {
		return create(Names.POWDER_DIRTY, material, tab);
	}

	// TODO: Possibly make this a placable Block
	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createCasing(MMDMaterial material, CreativeTabs tab) {
		return create(Names.CASING, material, tab);
	}

	// TODO: Possibly make this a Block, double of the normal plate.
	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createDensePlate(MMDMaterial material, CreativeTabs tab) {
		return create(Names.DENSE_PLATE, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createCrushed(MMDMaterial material, CreativeTabs tab) {
		return create(Names.CRUSHED, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createCrushedPurified(MMDMaterial material, CreativeTabs tab) {
		return create(Names.CRUSHED_PURIFIED, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createSlab(MMDMaterial material, CreativeTabs tab) {
		return create(Names.SLAB, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createAnvil(MMDMaterial material, CreativeTabs tab) {
		return create(Names.ANVIL, material, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material base of this item
	 * @param tab
	 *            which creative tab it is in
	 * @return the item this function created
	 */
	protected static Item createDoor(MMDMaterial material, CreativeTabs tab) {
		return create(Names.DOOR, material, tab);
	}

	/**
	 * Uses reflection to expand the size of the combat damage and attack speed
	 * arrays to prevent initialization index-out-of-bounds errors
	 *
	 * @param itemClass
	 *            The class to modify
	 */
	private static void expandCombatArrays(@Nonnull final Class<?> itemClass) throws IllegalAccessException, NoSuchFieldException {
		// WARNING: this method contains black magic
		final int expandedSize = 256;
		final Field[] fields = itemClass.getDeclaredFields();
		for (final Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()) && field.getType().isArray() && field.getType().getComponentType().equals(float.class)) {
				BaseMetals.logger.info("%s: Expanding array variable %s.%s to size %d", Thread.currentThread().getStackTrace()[0].toString(), itemClass.getSimpleName(), field.getName(), expandedSize);
				field.setAccessible(true); // bypass 'private' key word
				final Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL); // bypass 'final' keyword
				final float[] newArray = new float[expandedSize];
				Arrays.fill(newArray, 0F);
				System.arraycopy(field.get(null), 0, newArray, 0, Array.getLength(field.get(null)));
				field.set(null, newArray);
			}
		}
	}

	/**
	 *
	 * @param itemStack
	 *            The ItemStack
	 * @return The output
	 */
	public static int getSortingValue(@Nonnull final ItemStack itemStack) {
		int classVal = 990000;
		int materialVal = 9900;
		if ((itemStack.getItem() instanceof ItemBlock) && (((ItemBlock) itemStack.getItem()).getBlock() instanceof IMMDObject)) {
			classVal = classSortingValues.computeIfAbsent(((ItemBlock) itemStack.getItem()).getBlock().getClass(), (Class<?> clazz) -> 990000);
			materialVal = materialSortingValues.computeIfAbsent(((IMMDObject) ((ItemBlock) itemStack.getItem()).getBlock()).getMMDMaterial(), (MMDMaterial material) -> 9900);
		} else if (itemStack.getItem() instanceof IMMDObject) {
			classVal = classSortingValues.computeIfAbsent(itemStack.getItem().getClass(), (Class<?> clazz) -> 990000);
			materialVal = materialSortingValues.computeIfAbsent(((IMMDObject) itemStack.getItem()).getMMDMaterial(), (MMDMaterial material) -> 9900);
		}
		return classVal + materialVal + (itemStack.getMetadata() % 100);
	}

	protected static Class<? extends Item> getClassFromName(@Nonnull final Names name) {
		if (nameToClass.containsKey(name)) {
			return nameToClass.get(name);
		}
		return net.minecraft.item.Item.class;
	}

	protected static void mapNameToClass(@Nonnull final Names name, @Nonnull final Class<? extends Item> item) {
		if (!nameToClass.containsKey(name)) {
			nameToClass.put(name, item);
		}
	}

	protected static String getOredictFromName(@Nonnull final Names name) {
		if (nameToOredict.containsKey(name)) {
			return nameToOredict.get(name);
		}
		return null;
	}

	protected static void mapNameToOredict(@Nonnull final Names name, final String oredict) {
		if (!nameToOredict.containsKey(name)) {
			nameToOredict.put(name, oredict);
		}
	}

	protected static boolean isNameEnabled(@Nonnull final Names name) {
		if (nameToEnabled.containsKey(name)) {
			return nameToEnabled.get(name);
		}
		return false;
	}

	protected static void mapNameToEnabled(@Nonnull final Names name, @Nonnull final Boolean bool) {
		if (!nameToEnabled.containsKey(name)) {
			nameToEnabled.put(name, bool);
		}
	}

	/**
	 * Gets an item by its name. The name is the name as it is registered in the
	 * GameRegistry, not its unlocalized name (the unlocalized name is the
	 * registered name plus the prefix "basemetals.")
	 *
	 * @param name
	 *            The name of the item in question
	 * @return The item matching that name, or null if there isn't one
	 */
	public static Item getItemByName(@Nonnull final String name) {
		return itemRegistry.get(name);
	}

	/**
	 * This is the reverse of the getItemByName(...) method, returning the
	 * registered name of an item instance (Base Metals items only).
	 *
	 * @param item
	 *            The item in question
	 * @return The name of the item, or null if the item is not a Base Metals
	 *         item.
	 */
	public static String getNameOfItem(@Nonnull final Item item) {
		return itemRegistry.inverse().get(item);
	}

	public static Map<String, Item> getItemRegistry() {
		return Collections.unmodifiableMap(itemRegistry);
	}

	/**
	 * Gets a map of all items added, sorted by material
	 *
	 * @return An unmodifiable map of added items categorized by metal material
	 */
	public static Map<MMDMaterial, List<Item>> getItemsByMaterial() {
		return Collections.unmodifiableMap(itemsByMaterial);
	}
}
