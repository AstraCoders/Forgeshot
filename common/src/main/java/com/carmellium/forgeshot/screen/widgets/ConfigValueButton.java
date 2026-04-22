package com.carmellium.forgeshot.screen.widgets;

import com.carmellium.forgeshot.config.ConfigEntry;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class ConfigValueButton extends AbstractButton {

	private final ConfigEntry<Boolean> entry;
	private final Function<Boolean, Component> component;

	public ConfigValueButton(int i, int j, int k, int l, ConfigEntry<Boolean> entry, Function<Boolean, Component> component) {
		super(i, j, k, l, Component.empty());
		this.entry = entry;
		this.component = component;
		updateText();
	}

	private void updateText() {
		setMessage(component.apply(entry.get()));
	}

	@Override
	public void onPress(InputWithModifiers input) {
		entry.set(!entry.get());
		entry.save();
		updateText();
	}

	@Override
	protected void extractContents(GuiGraphicsExtractor guiGraphics, int i, int i1, float v) {
		extractDefaultSprite(guiGraphics);
		extractDefaultLabel(guiGraphics.textRendererForWidget(this, GuiGraphicsExtractor.HoveredTextEffects.NONE));
	}


	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
		defaultButtonNarrationText(narrationElementOutput);
	}
}
