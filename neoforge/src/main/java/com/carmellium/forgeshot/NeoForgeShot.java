package com.carmellium.forgeshot;


import com.carmellium.forgeshot.config.Config;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class NeoForgeShot {

	public NeoForgeShot(ModContainer container, IEventBus eventBus) {
		container.registerConfig(ModConfig.Type.CLIENT, Config.GENERAL_SPEC);
		// just for sanity
		if (FMLLoader.getCurrent().getDist().isClient()) {
			ForgeShot.init();
			NeoForgeShotEvents.init(eventBus);
		}
	}
}
