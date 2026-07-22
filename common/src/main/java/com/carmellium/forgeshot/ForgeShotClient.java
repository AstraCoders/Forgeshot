package com.carmellium.forgeshot;

import com.carmellium.forgeshot.screen.SettingsScreen;
import com.carmellium.forgeshot.tasks.CaptureTask;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 3/28/25
 */
public class ForgeShotClient {

	public static final ForgeShotClient INSTANCE = new ForgeShotClient();
	private CaptureTask task;

	private static final KeyMapping SCREENSHOT_KEY = new KeyMapping(
			"key.forgeshot.screenshot", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F4, KeyMapping.Category.MISC);
	private static final KeyMapping SCREENSHOT_GUI_KEY = new KeyMapping(
			"key.forgeshot.screenshot_gui", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F6, KeyMapping.Category.MISC);


	public void onRegisterBinding(Consumer<KeyMapping> event) {
		event.accept(SCREENSHOT_KEY);
		event.accept(SCREENSHOT_GUI_KEY);
	}

	public void onTick() {
		if (SCREENSHOT_KEY.consumeClick()) {
			capture();
		}
		if (SCREENSHOT_GUI_KEY.consumeClick()) {
			Minecraft.getInstance().gui.setScreen(new SettingsScreen());
		}
	}

	public void capture() {
		if (task == null) {
			task = new CaptureTask();
		}
	}

	public void onRender() {
		if (task != null && task.onRender()) {
			task = null;
		}
	}

	public float getScale() {
		if (task == null) {
			return 1;
		}
		return task.getScale();
	}
}
