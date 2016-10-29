package cyano.basemetals.init;

import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import cyano.basemetals.BaseMetals;
import cyano.basemetals.blocks.BlockMoltenFluid;
import cyano.basemetals.blocks.InteractiveFluidBlock;
import cyano.basemetals.fluids.CustomFluid;
import cyano.basemetals.material.MetalMaterial;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class initializes all fluids in Base Metals and provides some utility
 * methods for looking up fluids.
 *
 * @author DrCyano
 *
 */
public abstract class Fluids {

	static {
		FluidRegistry.enableUniversalBucket();
	}

	public static Fluid fluidAntimony = null;
	public static BlockFluidBase fluidBlockAntimony = null;

	public static Fluid fluidAdamantine = null;
	public static BlockFluidBase fluidBlockAdamantine = null;

	public static Fluid fluidAquarium = null;
	public static BlockFluidBase fluidBlockAquarium = null;

	public static Fluid fluidBismuth = null;
	public static BlockFluidBase fluidBlockBismuth = null;

	public static Fluid fluidBrass = null;
	public static BlockFluidBase fluidBlockBrass = null;

	public static Fluid fluidBronze = null;
	public static BlockFluidBase fluidBlockBronze = null;

	public static Fluid fluidColdIron = null;
	public static BlockFluidBase fluidBlockColdIron = null;

	public static Fluid fluidCopper = null;
	public static BlockFluidBase fluidBlockCopper = null;

	public static Fluid fluidCupronickel = null;
	public static BlockFluidBase fluidBlockCupronickel = null;

	public static Fluid fluidElectrum = null;
	public static BlockFluidBase fluidBlockElectrum = null;

	public static Fluid fluidInvar = null;
	public static BlockFluidBase fluidBlockInvar = null;

	public static Fluid fluidLead = null;
	public static BlockFluidBase fluidBlockLead = null;

	public static Fluid fluidMithril = null;
	public static BlockFluidBase fluidBlockMithril = null;

	public static Fluid fluidMercury = null;
	public static BlockFluidBase fluidBlockMercury = null;

	public static Fluid fluidNickel = null;
	public static BlockFluidBase fluidBlockNickel = null;

	public static Fluid fluidPewter = null;
	public static BlockFluidBase fluidBlockPewter = null;

	public static Fluid fluidPlatinum = null;
	public static BlockFluidBase fluidBlockPlatinum = null;

	public static Fluid fluidSilver = null;
	public static BlockFluidBase fluidBlockSilver = null;

	public static Fluid fluidStarSteel = null;
	public static BlockFluidBase fluidBlockStarSteel = null;

	public static Fluid fluidSteel = null;
	public static BlockFluidBase fluidBlockSteel = null;

	public static Fluid fluidTin = null;
	public static BlockFluidBase fluidBlockTin = null;

	public static Fluid fluidZinc = null;
	public static BlockFluidBase fluidBlockZinc = null;

	private static final BiMap<String, Fluid> fluidRegistry = HashBiMap.create();
	private static final BiMap<String, BlockFluidBase> fluidBlockRegistry = HashBiMap.create();

	private static final ResourceLocation dizzyPotionKey = new ResourceLocation("nausea");

	private static boolean initDone = false;

	/**
	 *
	 */
	public static void init() {
		if (initDone)
			return;

		// fluids
		fluidAdamantine = addFluid("adamantine", 2000, 10000, 330, 10);
		fluidAntimony = addFluid("antimony", 2000, 10000, 330, 10);
		fluidAquarium = addFluid("aquarium", 2000, 10000, 330, 10);
		fluidBismuth = addFluid("bismuth", 2000, 10000, 330, 10);
		fluidBrass = addFluid("brass", 2000, 10000, 330, 10);
		fluidBronze = addFluid("bronze", 2000, 10000, 330, 10);
		fluidColdIron = addFluid("coldiron", 2000, 10000, 330, 10);
		fluidCopper = addFluid("copper", 2000, 10000, 330, 10);
		fluidCupronickel = addFluid("cupronickel", 2000, 10000, 330, 10);
		fluidElectrum = addFluid("electrum", 2000, 10000, 330, 10);
		fluidInvar = addFluid("invar", 2000, 10000, 330, 10);
		fluidLead = addFluid("lead", 2000, 10000, 330, 10);
		fluidMercury = addFluid("mercury", 13594, 2000, 300, 0);
		fluidMithril = addFluid("mithril", 2000, 10000, 330, 10);
		fluidNickel = addFluid("nickel", 2000, 10000, 330, 10);
		fluidPewter = addFluid("pewter", 2000, 10000, 330, 10);
		fluidPlatinum = addFluid("platinum", 2000, 10000, 330, 10);
		fluidSilver = addFluid("silver", 2000, 10000, 330, 10);
		fluidStarSteel = addFluid("starsteel", 2000, 10000, 330, 10);
		fluidSteel = addFluid("steel", 2000, 10000, 330, 10);
		fluidTin = addFluid("tin", 2000, 10000, 330, 10);
		fluidZinc = addFluid("zinc", 2000, 10000, 330, 10);

		// fluid blocks
		fluidBlockAdamantine = addFluidBlock("adamantine");
		fluidBlockAntimony = addFluidBlock("antimony");
		fluidBlockAquarium = addFluidBlock("aquarium");
		fluidBlockBismuth = addFluidBlock("bismuth");
		fluidBlockBrass = addFluidBlock("brass");
		fluidBlockBronze = addFluidBlock("bronze");
		fluidBlockColdIron = addFluidBlock("coldiron");
		fluidBlockCopper = addFluidBlock("copper");
		fluidBlockCupronickel = addFluidBlock("cupronickel");
		fluidBlockElectrum = addFluidBlock("electrum");
		fluidBlockInvar = addFluidBlock("invar");
		fluidBlockLead = addFluidBlock("lead");
		fluidBlockMercury = addFluidBlock("mercury");
		fluidBlockMithril = addFluidBlock("mithril");
		fluidBlockNickel = addFluidBlock("nickel");
		fluidBlockPewter = addFluidBlock("pewter");
		fluidBlockPlatinum = addFluidBlock("platinum");
		fluidBlockSilver = addFluidBlock("silver");
		fluidBlockStarSteel = addFluidBlock("starsteel");
		fluidBlockSteel = addFluidBlock("steel");
		fluidBlockTin = addFluidBlock("tin");
		fluidBlockZinc = addFluidBlock("zinc");

		initDone = true;
	}

	private static Fluid addFluid(String name, int density, int viscosity, int temperature, int luminosity) {
		MetalMaterial metal = Materials.getMaterialByName(name);
		int tintColor = 0xFF000000;
		if (metal != null) { 
			tintColor = metal.getTintColor();
		} else {
			tintColor = 0xFFD8D8D8; // Hack for Mercury as it doesn't have a metalMaterial
		}

		final Fluid fluid = new CustomFluid(name, new ResourceLocation(BaseMetals.MODID + ":blocks/molten_metal_still"), new ResourceLocation(BaseMetals.MODID + ":blocks/molten_metal_flow"), tintColor);
		fluid.setDensity(density);
		fluid.setViscosity(viscosity);
		fluid.setTemperature(temperature);
		fluid.setLuminosity(luminosity);
		fluid.setUnlocalizedName(Loader.instance().activeModContainer().getModId() + "." + name);
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);

		fluidRegistry.put(name, fluid);
		return fluid;
	}

	private static BlockFluidBase addFluidBlock(String name) {

		final ResourceLocation location = new ResourceLocation(Loader.instance().activeModContainer().getModId(), name);
		Fluid fluid = getFluidByName(name);
		BlockFluidBase block;

		if (name != "mercury") {
			block = new BlockMoltenFluid(fluid);
		} else {
			block = new InteractiveFluidBlock(fluid, false, (World w, EntityLivingBase e) -> {
					if (w.rand.nextInt(32) == 0)
						e.addPotionEffect(new PotionEffect(Potion.REGISTRY.getObject(dizzyPotionKey), 30 * 20, 2));
				}); // , "liquid_mercury")
		}

		block.setRegistryName(location);
		block.setUnlocalizedName(location.toString());
		GameRegistry.register(block);
		block.setCreativeTab(CreativeTabs.MISC);

		final ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(location);
		itemBlock.setUnlocalizedName(location.toString());
		GameRegistry.register(itemBlock);

		fluidBlockRegistry.put(name, block);
		return block;
	}

	/**
	 * Gets a fluid by its name. The name is the name as it is registered in
	 * the GameRegistry, not its unlocalized name (the unlocalized name is the
	 * registered name plus the prefix "basemetals.")
	 *
	 * @param name The name of the fluid in question
	 * @return The fluid matching that name, or null if there isn't one
	 */
	public static Fluid getFluidByName(String name) {
		return fluidRegistry.get(name);
	}

	/**
	 * This is the reverse of the getFluidByName(...) method, returning the
	 * registered name of an fluid instance (Base Metals fluids only).
	 *
	 * @param b The item in question
	 * @return The name of the item, or null if the item is not a Base Metals
	 * fluid block.
	 */
	public static String getNameOfFluid(Fluid b) {
		return fluidRegistry.inverse().get(b);
	}

	public static Map<String, Fluid> getFluidRegistry() {
		return fluidRegistry;
	}

	/**
	 * Gets a fluid block by its name. The name is the name as it is registered in
	 * the GameRegistry, not its unlocalized name (the unlocalized name is the
	 * registered name plus the prefix "basemetals.")
	 *
	 * @param name The name of the fluid block in question
	 * @return The fluid block matching that name, or null if there isn't one
	 */
	public static BlockFluidBase getFluidBlockByName(String name) {
		return fluidBlockRegistry.get(name);
	}

	/**
	 * This is the reverse of the getFluidBlockByName(...) method, returning the
	 * registered name of an fluid block instance (Base Metals fluid blocks only).
	 *
	 * @param b The item in question
	 * @return The name of the item, or null if the item is not a Base Metals
	 * fluid block.
	 */
	public static String getNameOfFluidBlock(BlockFluidBase b) {
		return fluidBlockRegistry.inverse().get(b);
	}

	public static Map<String, BlockFluidBase> getFluidBlockRegistry() {
		return fluidBlockRegistry;
	}
}
