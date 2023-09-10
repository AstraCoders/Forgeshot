package com.lucasmellof.forgeshot.screen.components;

import com.lucasmellof.forgeshot.CommonRes;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class CustomResButton extends AbstractButton {
	private CommonRes res;
	private final Function<CommonRes, Component> component;

	public CustomResButton(int i, int j, int k, int l, CommonRes res, Function<CommonRes, Component> component) {
		super(i, j, k, l, Component.empty());
		this.component = component;
		this.res = res;
		updateText();
	}

	private void updateText() {
		setMessage(component.apply(res));
	}

	@Override
	public void onPress() {
		int index = res.ordinal();
		if (index == CommonRes.values().length - 1) {
			index = 0;
		} else {
			index++;
		}
		res = CommonRes.values()[index];
		updateText();
	}

	@Override
	public void updateNarration(@NotNull NarrationElementOutput narrationElementOutput) {
		defaultButtonNarrationText(narrationElementOutput);
	}
}
