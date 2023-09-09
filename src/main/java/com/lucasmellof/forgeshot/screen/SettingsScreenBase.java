package com.lucasmellof.forgeshot.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class SettingsScreenBase extends ScreenBase {

	protected ResourceLocation texture;

	private float opacity;

	public SettingsScreenBase(Component title, ResourceLocation texture, int xSize, int ySize) {
		super(title, xSize, ySize);
		this.texture = texture;
	}

	@Override
	protected void init() {
		super.init();
	}


	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1F, 1F, 1F, opacity);
		RenderSystem.setShaderTexture(0, texture);
		blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);

		super.render(matrixStack, mouseX, mouseY, partialTicks);

		int titleWidth = font.width(getTitle());
		font.draw(matrixStack, getTitle().getVisualOrderText(), guiLeft + (xSize - titleWidth) / 2, guiTop + 7, FONT_COLOR);

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
