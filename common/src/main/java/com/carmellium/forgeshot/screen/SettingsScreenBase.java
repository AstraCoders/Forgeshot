package com.carmellium.forgeshot.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class SettingsScreenBase extends ScreenBase {

	protected ResourceLocation texture;

	public SettingsScreenBase(Component title, ResourceLocation texture, int xSize, int ySize) {
		super(title, xSize, ySize);
		this.texture = texture;
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		super.renderBackground(graphics, mouseX, mouseY, partialTick);

		graphics.blit(RenderPipelines.GUI_TEXTURED, texture, guiLeft, guiTop, 0, 0, xSize, ySize, 256, 256);

	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		super.render(graphics, mouseX, mouseY, partialTicks);

		int titleWidth = font.width(getTitle());
		graphics.drawString(font, getTitle().getVisualOrderText(), guiLeft + (xSize - titleWidth) / 2, guiTop + 7, FONT_COLOR);
	}

	@Override
	public boolean mouseClicked(double d, double e, int i) {
		return super.mouseClicked(d, e, i);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
