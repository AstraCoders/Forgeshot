package com.lucasmellof.forgeshot.framebuffer;

import com.lucasmellof.forgeshot.callbacks.WriteCallback;
import com.lucasmellof.forgeshot.config.Config;
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
public class Writter {
    private final Capturer capturer;
    private final Path path;

    public Writter(Capturer capturer, Path path) {
        this.capturer = capturer;
        this.path = path;
    }

    public void save() throws IOException {
        try (var channel = FileChannel.open(
                path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            saveImg(channel);
        }

        File file = this.path.toFile();
        Component text = Component.literal(file.getName()).withStyle(ChatFormatting.UNDERLINE).withStyle(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file.getAbsolutePath())));
        Minecraft.getInstance().execute(() -> Minecraft.getInstance().gui.getChat().addMessage(Component.translatable("screenshot.success", text)));
    }

    private void saveImg(FileChannel channel) throws IOException {
        var dimension = capturer.getDimension();

        try (WriteCallback callback = new WriteCallback(channel)) {
            switch (Config.SAVE_FORMAT.get()) {
                case PNG -> STBImageWrite.stbi_write_png_to_func(
                        callback,
                        0,
                        dimension.width(),
                        dimension.height(),
                        Capturer.CHANNEL_COUNT,
                        capturer.getBuffer(),
                        0);
                case JPG -> STBImageWrite.stbi_write_jpg_to_func(
                        callback,
                        0,
                        dimension.width(),
                        dimension.height(),
                        Capturer.CHANNEL_COUNT,
                        capturer.getBuffer(),
                        90);
            }

            if (callback.exception() != null) {
                throw callback.exception();
            }
        }
    }
}
