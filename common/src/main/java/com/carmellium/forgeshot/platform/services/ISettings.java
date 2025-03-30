package com.carmellium.forgeshot.platform.services;

import com.carmellium.forgeshot.config.ConfigEntry;
import com.carmellium.forgeshot.config.SaveFormats;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 3/28/25
 */
public interface ISettings {
	ConfigEntry<Boolean> shouldHideHud();

	ConfigEntry<Boolean> shouldScaleHud();

	ConfigEntry<Integer> getWidth();

	ConfigEntry<Integer> getHeight();

	ConfigEntry<Integer> getDelay();

	ConfigEntry<SaveFormats> getSaveFormat();
}
