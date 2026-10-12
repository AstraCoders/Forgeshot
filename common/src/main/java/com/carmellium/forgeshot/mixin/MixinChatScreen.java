package com.carmellium.forgeshot.mixin;

import com.carmellium.forgeshot.Constants;
import com.carmellium.forgeshot.clipboard.ImageClipboard;
import com.carmellium.forgeshot.platform.Services;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 11/10/2026
 */
@Mixin(ChatScreen.class)
public class MixinChatScreen {
    @Inject(method = "handleComponentClicked", at = @At("HEAD"), cancellable = true)
    public void forgeshot$handleComponentClicked$1(
            Style clicked, boolean allowInsertions, CallbackInfoReturnable<Boolean> cir) {
        if (!(clicked.getClickEvent() instanceof ClickEvent.Custom(Identifier id, Optional<Tag> payload))) return;
        if (!Services.PLATFORM.replaceOriginalScreenshotMessage().get()) return;
        if (!id.equals(Constants.COPY_SCREENSHOT_IMAGE_CLICK_EVENT_ID)) return;

        Tag tag = payload.orElse(null);
        if (!(tag instanceof StringTag(String screenshotPath))) {
            Constants.LOG.warn("Expected StringTag for screenshot path, but got: {}", tag);
            return;
        }

        ImageClipboard.copyScreenshot(screenshotPath).exceptionally(error -> {
            Constants.LOG.error("Failed to copy screenshot image to clipboard", error);
            return null;
        });
        cir.setReturnValue(false);
    }
}
