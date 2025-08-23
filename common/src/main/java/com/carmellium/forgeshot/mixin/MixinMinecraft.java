package com.carmellium.forgeshot.mixin;

import com.carmellium.forgeshot.ForgeShotClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
@Mixin(Minecraft.class)
public class MixinMinecraft {
	@Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V"))
	public void onPreRender(boolean p_91384_, CallbackInfo ci) {
		ForgeShotClient.INSTANCE.onRender();
	}

	@Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V", shift = At.Shift.AFTER))
	public void onPostRender(boolean p_91384_, CallbackInfo ci) {
		ForgeShotClient.INSTANCE.onRender();
	}

	@ModifyArg(method = "resizeDisplay", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setGuiScale(I)V"))
	public int onResizeDisplay(int guiScale) {
		return (int) (ForgeShotClient.INSTANCE.getScale() * guiScale);
	}
}
