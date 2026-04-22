package com.carmellium.forgeshot.tasks;

import com.carmellium.forgeshot.Mine;
import com.carmellium.forgeshot.config.SaveFormats;
import com.carmellium.forgeshot.framebuffer.NativeWriter;
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
    private final int targetWidth;
    private final int targetHeight;
    private final int delay;
    private final boolean targetHideHud;
    private final boolean scaleHud;
    private final SaveFormats saveFormat;

    private int frame;

    private int displayWidth;
    private int displayHeight;

    private boolean hideHud;
    private boolean resized;

    public CaptureTask() {
        this.targetWidth = Services.PLATFORM.getWidth().get();
        this.targetHeight = Services.PLATFORM.getHeight().get();
        this.delay = Services.PLATFORM.getDelay().get();
        this.targetHideHud = Services.PLATFORM.shouldHideHud().get();
        this.scaleHud = Services.PLATFORM.shouldScaleHud().get();
        this.saveFormat = Services.PLATFORM.getSaveFormat().get();
        this.file = Mine.getScreenshotPath(saveFormat);
    }

    public float getScale() {
        if (!scaleHud || Minecraft.getInstance().options.guiScale().get() == 0) {
            return 1.0F;
        }
        return Math.min((float) Mine.getWidth() / displayWidth, (float) Mine.getHeight() / displayHeight);
    }

    public boolean onRender() {
        if (frame == 0) {
            displayWidth = Mine.getWidth();
            displayHeight = Mine.getHeight();
            hideHud = Minecraft.getInstance().options.hideGui;

            resized = targetWidth != displayWidth || targetHeight != displayHeight;
            if (resized) {
                Mine.resize(targetWidth, targetHeight);
            }
            Mine.hideHud(targetHideHud);
        } else if (frame >= delay) {
            try {
                RenderTarget target = Minecraft.getInstance().getMainRenderTarget();
                Screenshot.takeScreenshot(target, it -> Util.ioPool().execute(() -> {
                    try (it) {
                        var writer = new NativeWriter(it, file, saveFormat);
                        writer.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));
            } finally {
                if (resized) {
                    Mine.resize(displayWidth, displayHeight);
                }
                Mine.hideHud(hideHud);
            }
        }
        frame++;
        return frame > delay;
    }
}
