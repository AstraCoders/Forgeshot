package com.carmellium.forgeshot.screen.widgets;

import com.carmellium.forgeshot.config.ConfigEntry;
import net.minecraft.network.chat.Component;

import java.util.function.Function;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class ResolutionSlider extends IntegerConfigValueSlider {

    public ResolutionSlider(
            int x,
            int y,
            int width,
            int height,
            ConfigEntry<Integer> entry,
            int min,
            int max,
            Function<Integer, Component> text) {
        super(x, y, width, height, entry, min, max, text);
    }

    public void refetch() {
        this.value = getPercentage(min, max, this.entry.get());
        this.updateMessage();
    }
}
