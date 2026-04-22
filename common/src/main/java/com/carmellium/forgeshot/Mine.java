package com.carmellium.forgeshot;

import com.carmellium.forgeshot.mixin.AccessorWindow;
import com.carmellium.forgeshot.config.SaveFormats;
import com.carmellium.forgeshot.platform.Services;
import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class Mine {
	private static final Minecraft CLIENT = Minecraft.getInstance();
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

	public static int getWidth() {
		return CLIENT.getWindow().getWidth();
	}

	public static int getHeight() {
		return CLIENT.getWindow().getHeight();
	}

	public static void hideHud(boolean hideHud) {
		CLIENT.options.hideGui = hideHud;
	}

	public static void resize(int width, int height) {
		var accessor = (AccessorWindow) (Object) CLIENT.getWindow();

		accessor.setWidth(width);
		accessor.setHeight(height);
		accessor.setFramebufferWidth(width);
		accessor.setFramebufferHeight(height);
		CLIENT.getMainRenderTarget().resize(width, height);

		CLIENT.resizeDisplay();
	}

	public static Path getScreenshotPath() {
		return getScreenshotPath(Services.PLATFORM.getSaveFormat().get());
	}

	public static Path getScreenshotPath(SaveFormats saveFormat) {
		var dir = CLIENT.gameDirectory.toPath().resolve("screenshots");

		try {
			if (!Files.exists(dir)) Files.createDirectories(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Path path;
		// loop through suffixes while the file exists
		int suffix = 0;

		do {
			String suffixText = suffix == 0 ? "" : "_" + suffix;
			path = dir.resolve(
					"huge_" + DATE_FORMAT.format(new Date()) + suffixText + "." + saveFormat.name().toLowerCase());
			suffix++;
		} while (Files.exists(path));

		return path;
	}
}
