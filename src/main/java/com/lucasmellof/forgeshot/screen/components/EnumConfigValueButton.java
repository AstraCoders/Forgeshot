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
public class EnumConfigValueButton<T extends Enum<T>> extends AbstractButton {
	private final Class<T> clazz;
	private final ForgeConfigSpec.EnumValue<T> entry;
	private final Function<T, Component> component;

	public EnumConfigValueButton(int i, int j, int k, int l, ForgeConfigSpec.EnumValue<T> entry, Class<T> clazz, Function<T, Component> component) {
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
	public void onPress() {
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
	public void updateNarration(@NotNull NarrationElementOutput narrationElementOutput) {
		defaultButtonNarrationText(narrationElementOutput);
	}
}
