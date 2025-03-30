package com.carmellium.forgeshot.mixin;

import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
@Mixin(Window.class)
public interface AccessorWindow {

	@Accessor
	void setWidth(int width);

	@Accessor
	void setHeight(int height);

	@Accessor
	void setFramebufferWidth(int framebufferWidth);

	@Accessor
	void setFramebufferHeight(int framebufferHeight);
}
