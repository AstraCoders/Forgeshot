package com.carmellium.forgeshot;

import com.carmellium.forgeshot.mixin.AccessorWindow;
import com.carmellium.forgeshot.platform.Services;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.nio.ByteBuffer;
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

	public static void writeToBuffer(ByteBuffer buffer, int bytesPerPixel) {
		GL11.glReadPixels(0, 0, getWidth(), getHeight(), GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, buffer);

		var line = new byte[getWidth() * bytesPerPixel];
		var line1 = new byte[getWidth() * bytesPerPixel];

		// flip
		for (int i = 0; i < getHeight() / 2; i++) {
			int widthOffset = i * getWidth() * bytesPerPixel;
			int heightOffset = (getHeight() - i - 1) * getWidth() * bytesPerPixel;

			buffer.position(widthOffset);
			buffer.get(line);
			buffer.position(heightOffset);
			buffer.get(line1);

			buffer.position(heightOffset);
			buffer.put(line);
			buffer.position(widthOffset);
			buffer.put(line1);
		}
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

		CLIENT.resizeDisplay();
	}

	public static Path getScreenshotPath() {
		var dir = CLIENT.gameDirectory.toPath().resolve("screenshots");

		try {
			if (!Files.exists(dir)) Files.createDirectories(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Path path;
		// loop though suffixes while the file exists
		int i = 1;

		do {
			path = dir.resolve(
					"huge_" + DATE_FORMAT.format(new Date()) + (i++ == 1 ? "" : "_" + i) + "." + Services.PLATFORM.getSaveFormat().get().name().toLowerCase());
		} while (Files.exists(path));

		return path;
	}
}
