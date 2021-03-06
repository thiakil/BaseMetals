package com.mcmoddev.lib.integration.plugins.tinkers.modifiers;

import javax.annotation.Nonnull;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

// We really need nothing here other than to know its been applied.

public class ModifierLeadPlated extends ModifierTrait {

	public ModifierLeadPlated() {
		super("lead-plated", 0xFFFFFF, 1, 0);
	}

	@Override
	public void applyEffect(@Nonnull final NBTTagCompound rootCompound, @Nonnull final NBTTagCompound modifierTag) {
		modifierTag.setBoolean("plated", true);
		TagUtil.setExtraTag(rootCompound, modifierTag);
	}
}
