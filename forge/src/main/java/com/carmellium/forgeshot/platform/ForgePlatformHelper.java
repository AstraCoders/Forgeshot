package com.carmellium.forgeshot.platform;

import com.carmellium.forgeshot.config.Config;
import com.carmellium.forgeshot.config.ConfigEntry;
import com.carmellium.forgeshot.config.SaveFormats;
import com.carmellium.forgeshot.platform.services.IPlatformHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {
	private final ConfigEntry<Boolean> hideHud = asConfigEntry(Config.HIDE_HUD);
	private final ConfigEntry<Boolean> scaleHud = asConfigEntry(Config.SCALE_HUD);
	private final ConfigEntry<Integer> width = asConfigEntry(Config.WIDTH);
	private final ConfigEntry<Integer> height = asConfigEntry(Config.HEIGHT);
	private final ConfigEntry<Integer> delay = asConfigEntry(Config.DELAY);
	private final ConfigEntry<SaveFormats> saveFormat = asConfigEntry(Config.SAVE_FORMAT);

	@Override
	public String getPlatformName() {
		return "Forge";
	}

	@Override
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}

	@Override
	public boolean isDevelopmentEnvironment() {
		return !FMLLoader.isProduction();
	}
	private static <T> ConfigEntry<T> asConfigEntry(ForgeConfigSpec.ConfigValue<T> cfg) {
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
		return hideHud;
	}

	@Override
	public ConfigEntry<Boolean> shouldScaleHud() {
		return scaleHud;
	}

	@Override
	public ConfigEntry<Integer> getWidth() {
		return width;
	}

	@Override
	public ConfigEntry<Integer> getHeight() {
		return height;
	}

	@Override
	public ConfigEntry<Integer> getDelay() {
		return delay;
	}

	@Override
	public ConfigEntry<SaveFormats> getSaveFormat() {
		return saveFormat;
	}
}
