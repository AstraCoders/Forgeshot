package com.carmellium.forgeshot.clipboard;

import com.carmellium.forgeshot.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

public final class ImageClipboard {
    private static CompletableFuture<Void> clipboardQueue = CompletableFuture.completedFuture(null);

    private ImageClipboard() {}

    public static synchronized CompletableFuture<Void> copyScreenshot(String screenshotPath) {
        clipboardQueue = clipboardQueue.handle((ignored, error) -> null)
                .thenRunAsync(() -> copyScreenshotBlocking(screenshotPath));
        return clipboardQueue;
    }

    private static void copyScreenshotBlocking(String screenshotPath) {
        try {
            Path screenshotDirectory = Minecraft.getInstance().gameDirectory.toPath()
                    .resolve(Screenshot.SCREENSHOT_DIR).toRealPath();
            Path image = Path.of(screenshotPath).toRealPath();
            if (!Files.isRegularFile(image)
                    || !image.startsWith(screenshotDirectory)
                    || !image.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".png")) {
                throw new IOException("Refusing to copy non-screenshot image: " + screenshotPath);
            }

            String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
            if (os.startsWith("windows")) {
                copyOnWindows(image);
            } else if (os.startsWith("mac")) {
                run(new ProcessBuilder("osascript", "-e",
                        "on run argv\nset the clipboard to (read (POSIX file (item 1 of argv)) as «class PNGf»)\nend run",
                        image.toString()));
            } else if (os.startsWith("linux")) {
                copyOnLinux(image);
            } else {
                throw new IOException("Unsupported operating system: " + os);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CompletionException(e);
        } catch (IOException e) {
            throw new CompletionException(e);
        }
    }

    private static void copyOnWindows(Path image) throws IOException, InterruptedException {
        ProcessBuilder command = new ProcessBuilder("powershell.exe", "-NoProfile", "-STA", "-Command",
                "Add-Type -AssemblyName System.Windows.Forms; "
                        + "$image = [System.Drawing.Image]::FromFile($env:FORGESHOT_SCREENSHOT); "
                        + "try { [System.Windows.Forms.Clipboard]::SetImage($image) } "
                        + "finally { $image.Dispose() }");
        command.environment().put("FORGESHOT_SCREENSHOT", image.toString());
        run(command);
    }

    private static void copyOnLinux(Path image) throws IOException, InterruptedException {
        IOException waylandFailure = null;
        if (System.getenv("WAYLAND_DISPLAY") != null) {
            try {
                run(new ProcessBuilder("wl-copy", "--type", "image/png").redirectInput(image.toFile()));
                return;
            } catch (IOException e) {
                waylandFailure = e;
            }
        }

        if (System.getenv("DISPLAY") != null) {
            try {
                run(new ProcessBuilder("xclip", "-selection", "clipboard", "-t", "image/png", "-i", image.toString()));
                return;
            } catch (IOException e) {
                if (waylandFailure != null) e.addSuppressed(waylandFailure);
                throw e;
            }
        }

        // shortcut: Linux uses wl-copy/xclip provided by the desktop; add native protocol support if bundling is required.
        if (waylandFailure != null) throw waylandFailure;
        throw new IOException("Linux image clipboard requires wl-copy (Wayland) or xclip (X11)");
    }

    private static void run(ProcessBuilder command) throws IOException, InterruptedException {
        Process process = command.redirectOutput(ProcessBuilder.Redirect.INHERIT)
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start();
        try {
            if (!process.waitFor(15, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                throw new IOException("Clipboard command timed out after 15 seconds");
            }
            int exitCode = process.exitValue();
            if (exitCode != 0) throw new IOException("Clipboard command exited with status " + exitCode);
        } catch (InterruptedException e) {
            process.destroyForcibly();
            throw e;
        }
    }

    public static MutableComponent appendMessage(MutableComponent original, File file) {
        return original.append(" ")
                       .append(Component.translatable("forgeshot.screenshot.copy")
                                       .withStyle(it -> it.withClickEvent(new ClickEvent.Custom(
                                               Constants.COPY_SCREENSHOT_IMAGE_CLICK_EVENT_ID,
                                               Optional.of(StringTag.valueOf(file.getAbsolutePath()))))));
    }
}
