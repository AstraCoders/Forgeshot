package com.lucasmellof.forgeshot.screen.components;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Function;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class IntegerConfigValueSlider extends AbstractSliderButton {

	private final ForgeConfigSpec.IntValue entry;
	private final int min;
	private final int max;
	private final Function<Integer, Component> text;

	public IntegerConfigValueSlider(int x, int y, int width, int height, ForgeConfigSpec.IntValue entry, int min, int max, Function<Integer, Component> text) {
		super(x, y, width, height, Component.empty(), getPercentage(min, max, entry.get()));
		this.entry = entry;
		this.min = min;
		this.max = max;
		this.text = text;
		updateMessage();
	}

	@Override
	protected void updateMessage() {
		setMessage(getMsg());
	}

	public Component getMsg() {
		return text.apply(getValue(min, max, value));
	}

	@Override
	protected void applyValue() {
		entry.set(getValue(min, max, value));
		entry.save();
	}

	private static double getPercentage(int min, int max, int value) {
		return ((double) value - (double) min) / ((double) max - (double) min);
	}

	private static int getValue(int min, int max, double value) {
		return (int) ((double) min + value * ((double) max - (double) min));
	}
}
