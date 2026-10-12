package com.carmellium.forgeshot.mixin;

import com.carmellium.forgeshot.Constants;
import com.carmellium.forgeshot.platform.Services;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Screenshot;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.io.File;
import java.util.Optional;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 11/10/2026
 */
@Mixin(Screenshot.class)
public class MixinScreenshot {
    @ModifyExpressionValue(
            method = "lambda$grab$3",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;"))
    private static MutableComponent forgeshot$takeScreenshot$1(
            MutableComponent original, @Local(argsOnly = true) File file) {
        if (!Services.PLATFORM.replaceOriginalScreenshotMessage().get()) return original;
        return original.append(" ")
                .append(Component.translatable("forgeshot.screenshot.copy")
                        .withStyle(it -> it.withClickEvent(new ClickEvent.Custom(
                                Constants.COPY_SCREENSHOT_IMAGE_CLICK_EVENT_ID,
                                Optional.of(StringTag.valueOf(file.getAbsolutePath()))))));
    }
}
