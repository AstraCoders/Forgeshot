package com.carmellium.forgeshot.framebuffer;

import com.carmellium.forgeshot.callbacks.WriteCallback;
import com.carmellium.forgeshot.platform.Services;
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
public class NativeWritter {
    private final NativeImage capturer;
    private final Path path;

    public NativeWritter(NativeImage capturer, Path path) {
        this.capturer = capturer;
        this.path = path;
    }

    public void save() throws IOException {
        try (var channel = FileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            saveImg(channel);
        }

        File file = this.path.toFile();
        Component text = Component.literal(file.getName())
                .withStyle(ChatFormatting.UNDERLINE)
                .withStyle(style -> style.withClickEvent(new ClickEvent.OpenFile(file.getAbsolutePath())));
        Minecraft.getInstance().execute(() -> Minecraft.getInstance()
                .gui
                .getChat()
                .addMessage(Component.translatable("screenshot.success", text)));
    }

    private void saveImg(FileChannel channel) throws IOException {

        try (WriteCallback callback = new WriteCallback(channel)) {
            switch (Services.PLATFORM.getSaveFormat().get()) {
                case PNG -> STBImageWrite.nstbi_write_png_to_func(
                        callback.address(),
                        0,
                        this.capturer.getWidth(),
                        this.capturer.getHeight(),
                       this.capturer.format().components(),
                        capturer.getPointer(),
                        0);
                case JPG -> STBImageWrite.nstbi_write_png_to_func(
                        callback.address(),
                        0,
                        this.capturer.getWidth(),
                        this.capturer.getHeight(),
                        this.capturer.format().components(),
                        capturer.getPointer(),
                        90);
            }

            if (callback.exception() != null) {
                throw callback.exception();
            }
        }
    }
}
