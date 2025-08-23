package com.carmellium.forgeshot;

import com.carmellium.forgeshot.config.Config;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class ForgeShotForge {

	public ForgeShotForge(FMLJavaModLoadingContext ctx) {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.GENERAL_SPEC);
		// just for sanity
		if (FMLLoader.getDist().isClient()) {
			ForgeShot.init();
			ForgeShotEvents.init(ctx);
		}
	}
}
