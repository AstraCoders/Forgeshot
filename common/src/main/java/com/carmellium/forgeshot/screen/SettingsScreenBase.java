package com.carmellium.forgeshot.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.CommonColors;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class SettingsScreenBase extends ScreenBase {

	protected Identifier texture;

	public SettingsScreenBase(Component title, Identifier texture, int xSize, int ySize) {
		super(title, xSize, ySize);
		this.texture = texture;
	}

	@Override
	protected void init() {
		super.init();
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractBackground(graphics, mouseX, mouseY, a);
		graphics.blit(RenderPipelines.GUI_TEXTURED, texture, guiLeft, guiTop, 0, 0, xSize, ySize, 256, 256);
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractRenderState(graphics, mouseX, mouseY, a);
		extract(graphics, mouseX, mouseY, a);
	}

	public void extract(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		int titleWidth = font.width(getTitle());
		graphics.text(font, getTitle().getVisualOrderText(), guiLeft + (xSize - titleWidth) / 2, guiTop + 7, CommonColors.WHITE);
	}

	@Override
	public boolean mouseClicked(MouseButtonEvent event, boolean isDoubleClick) {
		return super.mouseClicked(event, isDoubleClick);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
