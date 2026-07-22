package com.carmellium.forgeshot.framebuffer;

import com.carmellium.forgeshot.callbacks.WriteCallback;
import com.carmellium.forgeshot.config.SaveFormats;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.stb.STBImageWrite;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class NativeWriter {
    private final NativeImage capturer;
    private final Path path;
    private final SaveFormats saveFormat;

    public NativeWriter(NativeImage capturer, Path path, SaveFormats saveFormat) {
        this.capturer = capturer;
        this.path = path;
        this.saveFormat = saveFormat;
    }

    public void save() throws IOException {
        try (var channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            saveImg(channel);
        }

        File file = this.path.toFile();
        Component text = Component.literal(file.getName())
                .withStyle(ChatFormatting.UNDERLINE)
                .withStyle(style -> style.withClickEvent(new ClickEvent.OpenFile(file.getAbsolutePath())));
        Minecraft.getInstance().execute(() -> Minecraft.getInstance()
                .gui
                .hud
                .getChat()
                .addClientSystemMessage(Component.translatable("screenshot.success", text)));
    }

    private void saveImg(FileChannel channel) throws IOException {

        try (WriteCallback callback = new WriteCallback(channel)) {
            boolean success = switch (saveFormat) {
                case PNG -> STBImageWrite.nstbi_write_png_to_func(
                        callback.address(),
                        0,
                        this.capturer.getWidth(),
                        this.capturer.getHeight(),
                        this.capturer.format().components(),
                        capturer.getPointer(),
                        this.capturer.getWidth() * this.capturer.format().components()) != 0;
                case JPG -> STBImageWrite.nstbi_write_jpg_to_func(
                        callback.address(),
                        0,
                        this.capturer.getWidth(),
                        this.capturer.getHeight(),
                        this.capturer.format().components(),
                        capturer.getPointer(),
                        90) != 0;
            };

            if (callback.exception() != null) {
                throw callback.exception();
            }
            if (!success) {
                throw new IOException("Failed to write screenshot as " + saveFormat);
            }
        }
    }
}
