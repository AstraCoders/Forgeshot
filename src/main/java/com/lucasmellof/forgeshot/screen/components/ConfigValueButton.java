package com.lucasmellof.forgeshot.screen.components;

import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class ConfigValueButton extends AbstractButton {

	private final ForgeConfigSpec.BooleanValue entry;
	private final Function<Boolean, Component> component;

	public ConfigValueButton(int i, int j, int k, int l, ForgeConfigSpec.BooleanValue entry, Function<Boolean, Component> component) {
		super(i, j, k, l, Component.empty());
		this.entry = entry;
		this.component = component;
		updateText();
	}

	private void updateText() {
		setMessage(component.apply(entry.get()));
	}

	@Override
	public void onPress() {
		entry.set(!entry.get());
		entry.save();
		updateText();
	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
		defaultButtonNarrationText(narrationElementOutput);
	}
}
