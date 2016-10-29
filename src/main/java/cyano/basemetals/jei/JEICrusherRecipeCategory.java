package cyano.basemetals.jei;

import cyano.basemetals.BaseMetals;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

/**
 *
 * @author Jasmine Iwanek
 *
 */
public class JEICrusherRecipeCategory extends BlankRecipeCategory {

	private final ResourceLocation bgtex = new ResourceLocation(BaseMetals.MODID + ":textures/gui/nei/nei_crusher.png");
	private final IDrawable background;
	private static final String ID = BaseMetalsJEIPlugin.JEIUID;

	private static final int INPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;

	/**
	 *
	 * @param guiHelper
	 */
	public JEICrusherRecipeCategory(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(this.bgtex, 24, 26, 128, 32);
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public String getTitle() {
		final String key = "nei." + BaseMetals.MODID + ".recipehandler.crusher.name";
		if (I18n.canTranslate(key))
			return net.minecraft.client.resources.I18n.format(key);
		else
			return "Crusher";
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Deprecated
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		final IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		final int x = 45;
		final int y = 7;
		guiItemStacks.init(INPUT_SLOT, true, x, y);
		guiItemStacks.init(OUTPUT_SLOT, false, x + 58, y);

		guiItemStacks.setFromRecipe(INPUT_SLOT, recipeWrapper.getInputs());
		guiItemStacks.setFromRecipe(OUTPUT_SLOT, recipeWrapper.getOutputs());
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		final IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		final int x = 45;
		final int y = 7;
		guiItemStacks.init(INPUT_SLOT, true, x, y);
		guiItemStacks.init(OUTPUT_SLOT, false, x + 58, y);

		guiItemStacks.setFromRecipe(INPUT_SLOT, recipeWrapper.getInputs());
		guiItemStacks.setFromRecipe(OUTPUT_SLOT, recipeWrapper.getOutputs());
	}
}
