package com.mcmoddev.basemetals.init;

import com.mcmoddev.basemetals.util.Config.Options;
import com.mcmoddev.lib.blocks.BlockHumanDetector;
import com.mcmoddev.lib.material.MetalMaterial;
import com.mcmoddev.lib.util.Oredicts;

import net.minecraft.block.*;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class initializes all blocks in Base Metals.
 *
 * @author Jasmine Iwanek
 *
 */
public class Blocks extends com.mcmoddev.lib.init.Blocks {

	public static Block charcoal_block;

	public static Block mercury_ore;

	public static Block human_detector;

	private static boolean initDone = false;

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

		Materials.init();
		ItemGroups.init();

		if (Options.enableAdamantine) {
			createBlocksFull(Materials.adamantine);
		}

		if (Options.enableAntimony) {
			createBlocksFull(Materials.antimony);
		}

		if (Options.enableAquarium) {
			createBlocksFull(Materials.aquarium);
		}

		if (Options.enableBismuth) {
			createBlocksFull(Materials.bismuth);
		}

		if (Options.enableBrass) {
			createBlocksFull(Materials.brass);
		}

		if (Options.enableBronze) {
			createBlocksFull(Materials.bronze);
		}

		if (Options.enableCharcoal) {
//			charcoal_block = createBlock(Materials.charcoal);
//			charcoal_block = addBlock(new Block(), "charcoal_block", null, ItemGroups.tab_blocks);
		}

		if (Options.enableCoal) {
//			Materials.vanilla_coal.block = net.minecraft.init.Blocks.COAL_BLOCK;
//			Materials.vanilla_coal.ore = net.minecraft.init.Blocks.COAL_ORE;
		}

		if (Options.enableColdIron) {
			createBlocksFull(Materials.coldiron);
		}

		if (Options.enableCopper) {
			createBlocksFull(Materials.copper);
		}

		if (Options.enableCupronickel) {
			createBlocksFull(Materials.cupronickel);
		}
		
		if (Options.enableDiamond) {
			final MetalMaterial material = Materials.vanilla_diamond;

			material.block = net.minecraft.init.Blocks.DIAMOND_BLOCK;
			material.ore = net.minecraft.init.Blocks.DIAMOND_ORE;

			createBars(material);
			createDoor(material);
			createTrapDoor(material);

			createBlocksAdditional(material);
		}

		if (Options.enableElectrum) {
			createBlocksFull(Materials.electrum);
		}

		if (Options.enableGold) {
			final MetalMaterial material = Materials.vanilla_gold;

			material.block = net.minecraft.init.Blocks.GOLD_BLOCK;
			material.ore = net.minecraft.init.Blocks.GOLD_ORE;
			material.pressure_plate = net.minecraft.init.Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE;

			createPlate(material);
			createBars(material);
			createDoor(material);
			createTrapDoor(material);

			createBlocksAdditional(material);
//			createButton(material);
//			createSlab(material);
//			createDoubleSlab(material);
//			createLever(material);
//			createStairs(material);
//			createWall(material);
		}

		if (Options.enableInvar) {
			createBlocksFull(Materials.invar);
		}

		if (Options.enableIron) {
			final MetalMaterial material = Materials.vanilla_iron;

			material.block = net.minecraft.init.Blocks.IRON_BLOCK;
			material.ore = net.minecraft.init.Blocks.IRON_ORE;
			material.bars = net.minecraft.init.Blocks.IRON_BARS;
			material.doorBlock = net.minecraft.init.Blocks.IRON_DOOR;
			material.trapdoor = net.minecraft.init.Blocks.IRON_TRAPDOOR;
			material.pressure_plate = net.minecraft.init.Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE;

			createPlate(material);

			createBlocksAdditional(material);
//			createButton(material);
//			createSlab(material);
//			createDoubleSlab(material);
//			createLever(material);
//			createStairs(material);
//			createWall(material);
		}

		if (Options.enableLead) {
			createBlocksFull(Materials.lead);
		}

		if (Options.enableMercury) {
			mercury_ore = addBlock(new BlockOre(), "mercury_ore", null, ItemGroups.tab_blocks);
			mercury_ore.setHardness(3.0f).setResistance(5.0f);
			OreDictionary.registerOre(Oredicts.OREMERCURY, mercury_ore);
		}

		if (Options.enableMithril) {
			createBlocksFull(Materials.mithril);
		}

		if (Options.enableNickel) {
			createBlocksFull(Materials.nickel);
		}

		if (Options.enablePewter) {
			createBlocksFull(Materials.pewter);
		}

		if (Options.enablePlatinum) {
			createBlocksFull(Materials.platinum);
		}

		if (Options.enableSilver) {
			createBlocksFull(Materials.silver);
		}

		if (Options.enableStarSteel) {
			createBlocksFull(Materials.starsteel);
			Materials.starsteel.block.setLightLevel(0.5f);
			Materials.starsteel.plate.setLightLevel(0.5f);
			Materials.starsteel.ore.setLightLevel(0.5f);
			Materials.starsteel.bars.setLightLevel(0.5f);
			Materials.starsteel.doorBlock.setLightLevel(0.5f);
			Materials.starsteel.trapdoor.setLightLevel(0.5f);
		}

		if (Options.enableSteel) {
			createBlocksFull(Materials.steel);
		}

		if (Options.enableStone) {
		}

		if (Options.enableTin) {
			createBlocksFull(Materials.tin);
		}

		if (Options.enableWood) {
		}

		if (Options.enableZinc) {
			createBlocksFull(Materials.zinc);
		}

		human_detector = addBlock(new BlockHumanDetector(), "human_detector", null, ItemGroups.tab_blocks);

		initDone = true;
	}
}
