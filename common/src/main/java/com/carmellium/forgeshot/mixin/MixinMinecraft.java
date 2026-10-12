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
@Mixin(value = Minecraft.class, remap = false)
public class MixinMinecraft {
	@Inject(
			//? if <26.1 {
			/*method = "runTick",
			*///?} else {
			method = "renderFrame",
			//?}
			at = @At(value = "INVOKE", target =
					//? if >=26.3 {
					/*"Lnet/minecraft/client/renderer/GameRenderer;render()V"))
					*///?} else {
					"Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V"))
					//?}
	public void onPreRender(boolean advanceGameTime, CallbackInfo ci) {
		ForgeShotClient.INSTANCE.onRender();
	}

	@Inject(
			//? if <26.1 {
			/*method = "runTick",
			*///?} else {
			method = "renderFrame",
			//?}
			at = @At(value = "INVOKE", target =
					//? if >=26.3 {
					/*"Lnet/minecraft/client/renderer/GameRenderer;render()V", shift = At.Shift.AFTER))
					*///?} else {
					"Lnet/minecraft/client/renderer/GameRenderer;render(Lnet/minecraft/client/DeltaTracker;Z)V", shift = At.Shift.AFTER))
					//?}
	public void onPostRender(boolean advanceGameTime, CallbackInfo ci) {
		ForgeShotClient.INSTANCE.onRender();
	}

	@ModifyArg(
			//? if <26.1 {
			/*method = "resizeDisplay",
			*///?} else {
			method = "resizeGui",
			//?}
			at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;setGuiScale(I)V"))
	public int onResizeDisplay(int guiScale) {
		return (int) (ForgeShotClient.INSTANCE.getScale() * guiScale);
	}
}
