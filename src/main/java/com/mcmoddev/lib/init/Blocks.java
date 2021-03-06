package com.mcmoddev.lib.init;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
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
import com.mcmoddev.lib.material.MMDMaterial;
import com.mcmoddev.lib.util.Oredicts;
import com.mcmoddev.lib.util.TabContainer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockSlab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class initializes all blocks in Base Metals and provides some utility
 * methods for looking up blocks.
 *
 * @author Jasmine Iwanek
 *
 */
public abstract class Blocks {

	private static boolean initDone = false;

	private static final BiMap<String, Block> blockRegistry = HashBiMap.create(16);
	private static final BiMap<String, BlockFluidBase> fluidBlockRegistry = HashBiMap.create(16);
	private static final Map<MMDMaterial, List<Block>> blocksByMaterial = new HashMap<>();

	private static final EnumMap<Names, Class<? extends Block>> nameToClass = new EnumMap<>(Names.class);
	private static final EnumMap<Names, String> nameToOredict = new EnumMap<>(Names.class);
	private static final EnumMap<Names, Boolean> nameToEnabled = new EnumMap<>(Names.class);

	protected Blocks() {
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
		
		mapNameToClass(Names.ANVIL, BlockMMDAnvil.class);
		mapNameToClass(Names.BARS, BlockMMDBars.class);
		mapNameToClass(Names.BLOCK, BlockMMDBlock.class);
		mapNameToClass(Names.BOOKSHELF, BlockMMDBookshelf.class);
		mapNameToClass(Names.BUTTON, BlockMMDButton.class);
		mapNameToClass(Names.DOOR, BlockMMDDoor.class);
		mapNameToClass(Names.DOUBLE_SLAB, BlockMMDSlab.class);
		mapNameToClass(Names.FLOWER_POT, BlockMMDFlowerPot.class);
		mapNameToClass(Names.LADDER, BlockMMDLadder.class);
		mapNameToClass(Names.LEVER, BlockMMDLever.class);
		mapNameToClass(Names.PLATE, BlockMMDPlate.class);
		mapNameToClass(Names.PRESSURE_PLATE, BlockMMDPressurePlate.class);
		mapNameToClass(Names.SLAB, BlockMMDSlab.class);
		mapNameToClass(Names.STAIRS, BlockMMDStairs.class);
		mapNameToClass(Names.TRAPDOOR, BlockMMDTrapDoor.class);
		mapNameToClass(Names.TRIPWIRE_HOOK, BlockMMDTripWireHook.class);
		mapNameToClass(Names.WALL, BlockMMDWall.class);
		mapNameToClass(Names.FENCE, BlockMMDFence.class);
		mapNameToClass(Names.FENCE_GATE, BlockMMDFenceGate.class);
		mapNameToClass(Names.ENDORE, BlockMMDOre.class);
		mapNameToClass(Names.NETHERORE, BlockMMDNetherOre.class);
		mapNameToClass(Names.ENDORE, BlockMMDEndOre.class);
		mapNameToClass(Names.ORE, BlockMMDOre.class);
		
		mapNameToOredict(Names.ANVIL, null);
		mapNameToOredict(Names.BARS, Oredicts.BARS);
		mapNameToOredict(Names.BLOCK, Oredicts.BLOCK);
		mapNameToOredict(Names.BOOKSHELF, null);
		mapNameToOredict(Names.BUTTON, Oredicts.BUTTON);
		mapNameToOredict(Names.DOOR, null);
		mapNameToOredict(Names.DOUBLE_SLAB, null);
		mapNameToOredict(Names.FLOWER_POT, null);
		mapNameToOredict(Names.LADDER, null);
		mapNameToOredict(Names.LEVER, Oredicts.LEVER);
		mapNameToOredict(Names.PLATE, Oredicts.PLATE);
		mapNameToOredict(Names.PRESSURE_PLATE, null);
		mapNameToOredict(Names.SLAB, null);
		mapNameToOredict(Names.STAIRS, Oredicts.STAIRS);
		mapNameToOredict(Names.TRAPDOOR, Oredicts.TRAPDOOR);
		mapNameToOredict(Names.TRIPWIRE_HOOK, null);
		mapNameToOredict(Names.WALL, Oredicts.WALL);
		mapNameToOredict(Names.FENCE, null);
		mapNameToOredict(Names.FENCE_GATE, null);
		mapNameToOredict(Names.ORE, Oredicts.ORE);
		mapNameToOredict(Names.NETHERORE, Oredicts.ORE_NETHER);
		mapNameToOredict(Names.ENDORE, Oredicts.ORE_END);

		final String basics = "Basics";
		final String wall = "Wall";

		mapNameToEnabled(Names.ANVIL, Options.isThingEnabled("Anvil"));
		mapNameToEnabled(Names.BARS, Options.isThingEnabled("Bars"));
		mapNameToEnabled(Names.BLOCK, Options.isThingEnabled(basics));
		mapNameToEnabled(Names.BOOKSHELF, Options.isThingEnabled("Bookshelf"));
		mapNameToEnabled(Names.BUTTON, Options.isThingEnabled("Button"));
		mapNameToEnabled(Names.DOOR, Options.isThingEnabled("Door"));
		mapNameToEnabled(Names.DOUBLE_SLAB, Options.isThingEnabled("Slab"));
		mapNameToEnabled(Names.FLOWER_POT, Options.isThingEnabled("FlowerPot"));
		mapNameToEnabled(Names.LADDER, Options.isThingEnabled("Ladder"));
		mapNameToEnabled(Names.LEVER, Options.isThingEnabled("Lever"));
		mapNameToEnabled(Names.PLATE, Options.isThingEnabled("Plate"));
		mapNameToEnabled(Names.PRESSURE_PLATE, Options.isThingEnabled("PressurePlate"));
		mapNameToEnabled(Names.SLAB, Options.isThingEnabled("Slab"));
		mapNameToEnabled(Names.STAIRS, Options.isThingEnabled("Stairs"));
		mapNameToEnabled(Names.TRAPDOOR, Options.isThingEnabled("Trapdoor"));
		mapNameToEnabled(Names.TRIPWIRE_HOOK, Options.isThingEnabled("TripWire"));
		mapNameToEnabled(Names.WALL, Options.isThingEnabled(wall));
		mapNameToEnabled(Names.FENCE, Options.isThingEnabled(wall));
		mapNameToEnabled(Names.FENCE_GATE, Options.isThingEnabled(wall));
		mapNameToEnabled(Names.ORE, Options.isThingEnabled(basics));
		mapNameToEnabled(Names.NETHERORE, Options.isThingEnabled(basics));
		mapNameToEnabled(Names.ENDORE, Options.isThingEnabled(basics));

		Materials.init();
		ItemGroups.init();

		initDone = true;
	}

	/**
	 * 
	 * @param materialName
	 *            The material of interest
	 * @param tabs
	 *            Container of the CreativeTabs these will get registered in
	 */
	protected static void createBlocksBasic(@Nonnull final String materialName, @Nonnull final TabContainer tabs) {
		createBlocksBasic(Materials.getMaterialByName(materialName), tabs);
	}

	/**
	 * 
	 * @param material
	 *            The material of interest
	 * @param tabs
	 *            Container of the CreativeTabs these will get registered in
	 */
	protected static void createBlocksBasic(@Nonnull final MMDMaterial material, @Nonnull final TabContainer tabs) {

		create(Names.BLOCK, material, tabs.blocksTab); // Not Gold, Not Iron, Not Diamond, Not Stone
		create(Names.PLATE, material, tabs.blocksTab);
		create(Names.ORE, material, tabs.blocksTab); // Not Gold, Not Iron, Not Diamond, Not Stone
		create(Names.BARS, material, tabs.blocksTab); // Not Iron
		create(Names.DOOR, material, tabs.blocksTab); // Not Iron
		create(Names.TRAPDOOR, material, tabs.blocksTab); // Not Iron
	}

	/**
	 * 
	 * @param materialName
	 *            The material of interest
	 * @param tabs
	 *            Container of the CreativeTabs these will get registered in
	 */
	protected static void createBlocksAdditional(@Nonnull final String materialName, @Nonnull final TabContainer tabs) {
		createBlocksAdditional(Materials.getMaterialByName(materialName), tabs);
	}

	/**
	 * 
	 * @param material
	 *            The material of interest
	 * @param tabs
	 *            Container of the CreativeTabs these will get registered in
	 */
	protected static void createBlocksAdditional(@Nonnull final MMDMaterial material, @Nonnull final TabContainer tabs) {
		if (tabs == null) {
			return;
		}

		create(Names.BUTTON, material, tabs.blocksTab);
		create(Names.SLAB, material, tabs.blocksTab);
		create(Names.DOUBLE_SLAB, material, tabs.blocksTab);
		create(Names.LEVER, material, tabs.blocksTab);
		create(Names.PRESSURE_PLATE, material, tabs.blocksTab); // Not Iron, Not Gold
		create(Names.STAIRS, material, tabs.blocksTab);
		create(Names.WALL, material, tabs.blocksTab);
	}

	/**
	 * 
	 * @param materialName
	 *            The material of interest
	 * @param tabs
	 *            Container of the CreativeTabs these will get registered in
	 */
	protected static void createBlocksFull(@Nonnull final String materialName, @Nonnull final TabContainer tabs) {
		createBlocksFull(Materials.getMaterialByName(materialName), tabs);
	}

	/**
	 * 
	 * @param material
	 *            The material of interest
	 * @param tabs
	 *            Container of the CreativeTabs these will get registered in
	 */
	protected static void createBlocksFull(@Nonnull final MMDMaterial material, @Nonnull final TabContainer tabs) {
		createBlocksBasic(material, tabs);
		createBlocksAdditional(material, tabs);
	}

	protected static Block create(@Nonnull final Names name, @Nonnull final String materialName, final CreativeTabs tab) {
		return create(name, Materials.getMaterialByName(materialName), false, tab);
	}

	/**
	 * 
	 * @param name
	 *            Name of the requested block type
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block create(@Nonnull final Names name, @Nonnull final MMDMaterial material, final CreativeTabs tab) {
		return create(name, material, false, tab);
	}

	protected static Block create(@Nonnull final Names name, @Nonnull final String materialName, @Nonnull final boolean glow, final CreativeTabs tab) {
		return create(name, Materials.getMaterialByName(materialName), glow, tab);
	}

	/**
	 * 
	 * @param name
	 *            Name of the requested block type
	 * @param material
	 *            The material this is made from
	 * @param glow
	 *            Does it have a glow ?
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block create(@Nonnull final Names name, @Nonnull final MMDMaterial material, @Nonnull final boolean glow, final CreativeTabs tab) {
		if (material.hasBlock(name)) {
			return material.getBlock(name);
		}

		if ((name.equals(Names.BLOCK)) && (isNameEnabled(name))) {
			material.addNewBlock(name, addBlock(new BlockMMDBlock(material, glow, true), name.toString(), material, tab));
			return material.getBlock(name);
		}

		if ((((name.equals(Names.ANVIL)) || (name.equals(Names.FENCE)) || (name.equals(Names.FENCE_GATE)) || (name.equals(Names.FLOWER_POT))
				|| (name.equals(Names.LADDER)) || (name.equals(Names.STAIRS)) || (name.equals(Names.TRIPWIRE_HOOK)) || (name.equals(Names.WALL)))
				&& (!material.hasBlock(Names.BLOCK)))
			|| (((name.equals(Names.ORE)) || name.equals(Names.ENDORE) || name.equals(Names.NETHERORE)) && (!material.hasOre()))) {
			return null;
		}

		final Block block = createBlock(material, name.toString(), getClassFromName(name), isNameEnabled(name), tab);

		final String oredict = getOredictFromName(name);
		if ((oredict != null) && (block != null)) {
			Oredicts.registerOre(oredict + material.getCapitalizedName(), block);
		}

		return block;
	}

	protected static Block addBlock(@Nonnull final Block block, @Nonnull final Names name, final CreativeTabs tab) {
		return addBlock(block, name.toString(), null, tab);
	}

	protected static Block addBlock(@Nonnull final Block block, @Nonnull final String name, final CreativeTabs tab) {
		return addBlock(block, name, null, tab);
	}

	/**
	 * 
	 * @param block
	 *            the block of interest
	 * @param name
	 *            name of the thing
	 * @param material
	 *            the material the thing is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return a new block
	 */
	protected static Block addBlock(@Nonnull final Block block, @Nonnull final Names name, final MMDMaterial material, final CreativeTabs tab) {
		return addBlock(block, name.toString(), material, tab);
	}

	/**
	 * 
	 * @param block
	 *            the block of interest
	 * @param name
	 *            name of the thing
	 * @param material
	 *            the material the thing is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return a new block
	 */
	protected static Block addBlock(@Nonnull final Block block, @Nonnull final String name, final MMDMaterial material, final CreativeTabs tab) {
		String fullName;

		if (material != null) {
			if (block instanceof BlockMMDDoubleSlab) {
				fullName = "double_" + material.getName() + "_" + Names.SLAB;
			} else if ((name.startsWith("nether")) || (name.startsWith("end"))) {
				String neededBit = name.substring(0, name.length() - 3);
				fullName = neededBit + "_" + material.getName() + "_" + "ore";
			} else {
				fullName = material.getName() + "_" + name;
			}
		} else if (block instanceof BlockMMDDoubleSlab) {
			fullName = "double_" + name;
		} else {
			fullName = name;
		}

		block.setRegistryName(fullName);
		block.setUnlocalizedName(block.getRegistryName().getResourceDomain() + "." + fullName);
		GameRegistry.register(block);
		if (block instanceof BlockFluidBase) {
			fluidBlockRegistry.put(fullName, (BlockFluidBase) block);
		} else {
			blockRegistry.put(fullName, block);
		}

		if (!(block instanceof BlockAnvil) && !(block instanceof BlockDoor) && !(block instanceof BlockSlab)) {
			final ItemBlock itemBlock = new ItemBlock(block);
			itemBlock.setRegistryName(fullName);
			itemBlock.setUnlocalizedName(block.getRegistryName().getResourceDomain() + "." + fullName);
			GameRegistry.register(itemBlock);
		}

		if (tab != null) {
			block.setCreativeTab(tab);
		}

		if (material != null) {
			blocksByMaterial.computeIfAbsent(material, (MMDMaterial g) -> new ArrayList<>());
			blocksByMaterial.get(material).add(block);
		}

		return block;
	}

	private static Block createBlock(@Nonnull final MMDMaterial material, @Nonnull final String name, @Nonnull final Class<? extends Block> clazz, @Nonnull final boolean enabled, final CreativeTabs tab) {
		if (enabled) {
			Constructor<?> ctor = null;
			Block inst = null;
			try {
				ctor = clazz.getConstructor(material.getClass());
			} catch (Exception ex) {
				BaseMetals.logger.fatal("Class for Block named " + name + " does not have an accessible constructor or another exception occurred", ex);
				return null;
			}

			try {
				inst = (Block) ctor.newInstance(material);
			} catch (Exception ex) {
				BaseMetals.logger.fatal("Unable to create Block named " + name + " for material " + material.getCapitalizedName(), ex);
			}

			if (inst != null) {
				material.addNewBlock(name, addBlock(inst, name, material, tab));
				return inst;
			}
		}

		return null;
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createBookshelf(MMDMaterial material, CreativeTabs tab) {
		return create(Names.BOOKSHELF, material, false, tab);
	}

	protected static Block createBookshelf(@Nonnull final MMDMaterial material, @Nonnull final boolean fullBlock, final CreativeTabs tab) {

		BlockMMDBookshelf bs = (BlockMMDBookshelf) create(Names.BOOKSHELF, material, tab);
		if (bs != null) {
			bs.setFullBlock(fullBlock);
		}
		return bs;
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createPlate(MMDMaterial material, CreativeTabs tab) {
		return create(Names.PLATE, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createBars(MMDMaterial material, CreativeTabs tab) {
		return create(Names.BARS, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createBlock(MMDMaterial material, CreativeTabs tab) {
		return create(Names.BLOCK, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param glow
	 *            Does it have a glow ?
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createBlock(MMDMaterial material, boolean glow, CreativeTabs tab) {
		return create(Names.BLOCK, material, glow, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createButton(MMDMaterial material, CreativeTabs tab) {
		return create(Names.BUTTON, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createLever(MMDMaterial material, CreativeTabs tab) {
		return create(Names.LEVER, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createPressurePlate(MMDMaterial material, CreativeTabs tab) {
		return create(Names.PRESSURE_PLATE, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createSlab(MMDMaterial material, CreativeTabs tab) {
		return create(Names.SLAB, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createDoubleSlab(MMDMaterial material, CreativeTabs tab) {
		return create(Names.DOUBLE_SLAB, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createStairs(MMDMaterial material, CreativeTabs tab) {
		return create(Names.STAIRS, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createWall(MMDMaterial material, CreativeTabs tab) {
		return create(Names.WALL, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createFence(MMDMaterial material, CreativeTabs tab) {
		return create(Names.FENCE, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createFenceGate(MMDMaterial material, CreativeTabs tab) {
		return create(Names.FENCE_GATE, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createAnvil(MMDMaterial material, CreativeTabs tab) {
		return create(Names.ANVIL, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createFlowerPot(MMDMaterial material, CreativeTabs tab) {
		return create(Names.FLOWER_POT, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createLadder(MMDMaterial material, CreativeTabs tab) {
		return create(Names.LADDER, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createTripWireHook(MMDMaterial material, CreativeTabs tab) {
		return create(Names.TRIPWIRE_HOOK, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createOre(MMDMaterial material, CreativeTabs tab) {
		return create(Names.ORE, material, false, tab);
	}

	/**
	 * This is here purely for End Metals
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createEndOre(MMDMaterial material, CreativeTabs tab) {
		return create(Names.ENDORE, material, false, tab);
	}

	/**
	 * This is here purely for Nether Metals
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createNetherOre(MMDMaterial material, CreativeTabs tab) {
		return create(Names.NETHERORE, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createDoor(MMDMaterial material, CreativeTabs tab) {
		// oreDict is handled in items
		return create(Names.DOOR, material, false, tab);
	}

	/**
	 * 
	 * @param material
	 *            The material this is made from
	 * @param tab
	 *            which creative tab is it on
	 * @return the block this function created
	 */
	protected static Block createTrapDoor(MMDMaterial material, CreativeTabs tab) {
		return create(Names.TRAPDOOR, material, false, tab);
	}

	protected static Class<? extends Block> getClassFromName(@Nonnull final Names name) {
		if (nameToClass.containsKey(name)) {
			return nameToClass.get(name);
		}
		return net.minecraft.block.Block.class;
	}

	protected static void mapNameToClass(@Nonnull final Names name, @Nonnull final Class<? extends Block> block) {
		if (!nameToClass.containsKey(name)) {
			nameToClass.put(name, block);
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
	 * Gets an block by its name. The name is the name as it is registered in
	 * the GameRegistry, not its unlocalized name (the unlocalized name is the
	 * registered name plus the prefix "basemetals.")
	 *
	 * @param name
	 *            The name of the block in question
	 * @return The block matching that name, or null if there isn't one
	 */
	public static Block getBlockByName(@Nonnull final String name) {
		return blockRegistry.get(name);
	}

	/**
	 * This is the reverse of the getBlockByName(...) method, returning the
	 * registered name of an block instance (Base Metals blocks only).
	 *
	 * @param block
	 *            The block in question
	 * @return The name of the block, or null if the item is not a Base Metals
	 *         block.
	 */
	public static String getNameOfBlock(@Nonnull final Block block) {
		return blockRegistry.inverse().get(block);
	}

	public static Map<String, Block> getBlockRegistry() {
		return Collections.unmodifiableMap(blockRegistry);
	}

	/**
	 * Gets a map of all blocks added, sorted by material
	 *
	 * @return An unmodifiable map of added items categorized by material
	 */
	public static Map<MMDMaterial, List<Block>> getBlocksByMaterial() {
		return Collections.unmodifiableMap(blocksByMaterial);
	}
}
