package com.mcmoddev.lib.integration.plugins;

import com.mcmoddev.lib.integration.IIntegration;

public class Thaumcraft implements IIntegration {

	public static final String PLUGIN_MODID = "thaumcraft";

	private static boolean initDone = false;

	@Override
	public void init() {
		if (initDone || !com.mcmoddev.basemetals.util.Config.Options.enableThaumcraft) {
			return;
		}

		initDone = true;
	}
}
