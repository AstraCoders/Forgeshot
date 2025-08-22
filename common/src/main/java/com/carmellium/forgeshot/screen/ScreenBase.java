package com.carmellium.forgeshot.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class ScreenBase extends Screen {

	protected static final int FONT_COLOR = 0xffffff;

	protected int guiLeft;
	protected int guiTop;
	protected int xSize;
	protected int ySize;

	protected ScreenBase(Component title, int xSize, int ySize) {
		super(title);
		this.xSize = xSize;
		this.ySize = ySize;
	}


	@Override
	protected void init() {
		super.init();
		this.guiLeft = (width - this.xSize) / 2;
		this.guiTop = (height - this.ySize) / 2;
	}

	public int getGuiLeft() {
		return guiLeft;
	}

	public int getGuiTop() {
		return guiTop;
	}

	public static class HoverArea {
		private final int posX, posY;
		private final int width, height;
		@Nullable
		private final Supplier<List<FormattedCharSequence>> tooltip;

		public HoverArea(int posX, int posY, int width, int height) {
			this(posX, posY, width, height, null);
		}

		public HoverArea(int posX, int posY, int width, int height, Supplier<List<FormattedCharSequence>> tooltip) {
			this.posX = posX;
			this.posY = posY;
			this.width = width;
			this.height = height;
			this.tooltip = tooltip;
		}

		public int getPosX() {
			return posX;
		}

		public int getPosY() {
			return posY;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		@Nullable
		public Supplier<List<FormattedCharSequence>> getTooltip() {
			return tooltip;
		}

		public boolean isHovered(int guiLeft, int guiTop, int mouseX, int mouseY) {
			if (mouseX >= guiLeft + posX && mouseX < guiLeft + posX + width) {
				return mouseY >= guiTop + posY && mouseY < guiTop + posY + height;
			}
			return false;
		}
	}

}
