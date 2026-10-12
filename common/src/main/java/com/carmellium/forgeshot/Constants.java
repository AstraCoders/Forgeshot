package com.carmellium.forgeshot;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	public static final Identifier COPY_SCREENSHOT_IMAGE_CLICK_EVENT_ID = Constants.of("copy_screenshot_image");
	public static Identifier of(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static final String MOD_ID = "forgeshot";
	public static final String MOD_NAME = "forgeshot";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
}
