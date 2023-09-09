package com.lucasmellof.forgeshot.tasks;

import com.lucasmellof.forgeshot.Mine;
import com.lucasmellof.forgeshot.config.Config;
import com.lucasmellof.forgeshot.framebuffer.Capturer;
import com.lucasmellof.forgeshot.framebuffer.Writter;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;

import java.nio.file.Path;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class CaptureTask {
	private final Path file;

	private int frame;

	private int displayWidth;
	private int displayHeight;

	private boolean hideHud;

	public CaptureTask(Path file) {
		this.file = file;
	}

	public float getScale() {
		if (Minecraft.getInstance().options.guiScale().get() == 0) {
			return 1.0F;
		}
		return Math.min((float) Mine.getWidth() / displayWidth, (float) Mine.getHeight() / displayHeight);
	}

	public boolean onRender() {
		if (frame == 0) {
			displayWidth = Mine.getWidth();
			displayHeight = Mine.getHeight();
			hideHud = Minecraft.getInstance().options.hideGui;

			int width = Config.WIDTH.get();
			int height = Config.HEIGHT.get();

			Mine.resize(width, height);
			Mine.hideHud(Config.HIDE_HUD.get());
		} else if (frame >= Config.DELAY.get()) {
			try {
				Capturer capture = new Capturer();
				capture.capture();

				Util.ioPool().execute(() -> {
					var writter = new Writter(capture, file);

					try {
						writter.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			} finally {
				Mine.resize(displayWidth, displayHeight);
				Mine.hideHud(hideHud);
			}
		}
		frame++;
		return frame > Config.DELAY.get();
	}

}
