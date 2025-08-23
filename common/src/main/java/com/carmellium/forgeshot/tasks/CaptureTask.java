package com.carmellium.forgeshot.tasks;

import com.carmellium.forgeshot.Mine;
import com.carmellium.forgeshot.framebuffer.NativeWritter;
import com.carmellium.forgeshot.platform.Services;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;

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

            int width = Services.PLATFORM.getWidth().get();
            int height = Services.PLATFORM.getHeight().get();

            Mine.resize(width, height);
            Mine.hideHud(Services.PLATFORM.shouldHideHud().get());
        } else if (frame >= Services.PLATFORM.getDelay().get()) {
            try {
                RenderTarget target = Minecraft.getInstance().getMainRenderTarget();
                Screenshot.takeScreenshot(target, it -> Util.ioPool().execute(() -> {
					var writter = new NativeWritter(it, file);

					try {
						writter.save();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}));
            } finally {
                Mine.resize(displayWidth, displayHeight);
                Mine.hideHud(hideHud);
            }
        }
        frame++;
        return frame > Services.PLATFORM.getDelay().get();
    }
}
