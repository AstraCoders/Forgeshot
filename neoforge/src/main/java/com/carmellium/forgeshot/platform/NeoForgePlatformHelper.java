package com.carmellium.forgeshot.platform;

import com.carmellium.forgeshot.config.Config;
import com.carmellium.forgeshot.config.ConfigEntry;
import com.carmellium.forgeshot.config.SaveFormats;
import com.carmellium.forgeshot.platform.services.IPlatformHelper;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgePlatformHelper implements IPlatformHelper {

	@Override
	public String getPlatformName() {
		return "NeoForge";
	}

	@Override
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}

	@Override
	public boolean isDevelopmentEnvironment() {
		return !FMLLoader.getCurrent().isProduction();
	}

	public static <T> ConfigEntry<T> AsConfigEntry(ModConfigSpec.ConfigValue<T> cfg) {
		return new ConfigEntry<>() {
			@Override
			public T get() {
				return cfg.get();
			}

			@Override
			public void set(T value) {
				cfg.set(value);
			}

			@Override
			public void save() {
				cfg.save();
			}
		};
	}

	@Override
	public ConfigEntry<Boolean> shouldHideHud() {
		return AsConfigEntry(Config.HIDE_HUD);
	}

	@Override
	public ConfigEntry<Boolean> shouldScaleHud() {
		return AsConfigEntry(Config.SCALE_HUD);
	}

	@Override
	public ConfigEntry<Integer> getWidth() {
		return AsConfigEntry(Config.WIDTH);
	}

	@Override
	public ConfigEntry<Integer> getHeight() {
		return AsConfigEntry(Config.HEIGHT);
	}

	@Override
	public ConfigEntry<Integer> getDelay() {
		return AsConfigEntry(Config.DELAY);
	}

	@Override
	public ConfigEntry<SaveFormats> getSaveFormat() {
		return AsConfigEntry(Config.SAVE_FORMAT);
	}
}
