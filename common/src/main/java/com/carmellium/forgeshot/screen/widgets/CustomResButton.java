package com.carmellium.forgeshot.screen.widgets;

import com.carmellium.forgeshot.CommonResolutions;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class CustomResButton extends AbstractButton {
	private CommonResolutions res;
	private final Function<CommonResolutions, Component> component;

	public CustomResButton(int i, int j, int k, int l, CommonResolutions res, Function<CommonResolutions, Component> component) {
		super(i, j, k, l, Component.empty());
		this.component = component;
		this.res = res;
		updateText();
	}

	private void updateText() {
		setMessage(component.apply(res));
	}

	@Override
	public void onPress(InputWithModifiers inputWithModifiers) {
		int index = res.ordinal();
		if (index == CommonResolutions.values().length - 1) {
			index = 0;
		} else {
			index++;
		}
		res = CommonResolutions.values()[index];
		updateText();
	}

	@Override
	protected void renderContents(GuiGraphics guiGraphics, int i, int i1, float v) {
		renderDefaultSprite(guiGraphics);
		renderDefaultLabel(guiGraphics.textRendererForWidget(this, GuiGraphics.HoveredTextEffects.NONE));
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
		defaultButtonNarrationText(narrationElementOutput);
	}
}
