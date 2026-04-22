package com.carmellium.forgeshot.config;


import net.neoforged.neoforge.common.ModConfigSpec;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class Config {
	public static final ModConfigSpec GENERAL_SPEC;

	public static ModConfigSpec.BooleanValue HIDE_HUD;
	public static ModConfigSpec.BooleanValue SCALE_HUD;

	public static ModConfigSpec.IntValue WIDTH;
	public static ModConfigSpec.IntValue HEIGHT;

	public static ModConfigSpec.IntValue DELAY;

	public static ModConfigSpec.EnumValue<SaveFormats> SAVE_FORMAT;


	static {
		ModConfigSpec.Builder configBuilder = new ModConfigSpec.Builder();
		setupConfig(configBuilder);
		GENERAL_SPEC = configBuilder.build();
	}

	private static void setupConfig(ModConfigSpec.Builder builder) {
		builder.comment("General settings").push("general");

		HIDE_HUD = builder.comment("Hide HUD when taking screenshot").define("hideHud", true);
		SCALE_HUD = builder.comment("Scale HUD when taking screenshot").define("scaleHud", true);

		WIDTH = builder.comment("Width of the screenshot").defineInRange("width", 3840, 1, 15360);
		HEIGHT = builder.comment("Height of the screenshot").defineInRange("height", 2160, 1, 8640);

		DELAY = builder.comment("Delay before taking screenshot").defineInRange("delay", 3, 0, 10);

		SAVE_FORMAT = builder.comment("Save format of the screenshot").defineEnum("saveFormat", SaveFormats.PNG);

		builder.pop();
	}
}
