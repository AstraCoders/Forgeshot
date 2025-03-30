package com.carmellium.forgeshot.screen.widgets;

import com.carmellium.forgeshot.config.ConfigEntry;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class IntegerConfigValueSlider extends AbstractSliderButton {

	protected final ConfigEntry<Integer> entry;
	protected final int min;
	protected final int max;
	private final Function<Integer, Component> text;

	public IntegerConfigValueSlider(int x, int y, int width, int height, ConfigEntry<Integer> entry, int min, int max, Function<Integer, Component> text) {
		super(x, y, width, height, Component.empty(), getPercentage(min, max, entry.get()));
		this.entry = entry;
		this.min = min;
		this.max = max;
		this.text = text;
		updateMessage();
	}

	@Override
	public void updateMessage() {
		setMessage(getMsg());
	}

	public Component getMsg() {
		int value1 = getValue(min, max, value);
		return text.apply(value1);
	}

	@Override
	protected void applyValue() {
		int value1 = getValue(min, max, value);
		entry.set(value1);
		entry.save();
	}

	public static double getPercentage(int min, int max, int value) {
		return ((double) value - (double) min) / ((double) max - (double) min);
	}

	public static int getValue(int min, int max, double value) {
		return (int) ((double) min + value * ((double) max - (double) min));
	}

	public int getValue() {
		return getValue(min, max, this.value);
	}
}
