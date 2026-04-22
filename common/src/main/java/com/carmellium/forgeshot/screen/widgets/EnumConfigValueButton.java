package com.carmellium.forgeshot.screen.widgets;

import com.carmellium.forgeshot.config.ConfigEntry;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Function;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class EnumConfigValueButton<T extends Enum<T>> extends AbstractButton {
	private final Class<T> clazz;
	private final ConfigEntry<T> entry;
	private final Function<T, Component> component;

	public EnumConfigValueButton(int i, int j, int k, int l, ConfigEntry<T> entry, Class<T> clazz, Function<T, Component> component) {
		super(i, j, k, l, Component.empty());
		this.entry = entry;
		this.component = component;
		this.clazz = clazz;
		updateText();
	}

	private void updateText() {
		setMessage(component.apply(entry.get()));
	}

	@Override
	public void onPress(InputWithModifiers input) {
		int index = entry.get().ordinal();
		int max = clazz.getEnumConstants().length - 1;
		if (index == max) {
			index = 0;
		} else {
			index++;
		}

		entry.set(clazz.getEnumConstants()[index]);
		entry.save();
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
