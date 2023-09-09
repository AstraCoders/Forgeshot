package com.lucasmellof.forgeshot.screen;

import com.lucasmellof.forgeshot.ForgeShot;
import com.lucasmellof.forgeshot.config.Config;
import com.lucasmellof.forgeshot.config.SaveFormats;
import com.lucasmellof.forgeshot.screen.components.ConfigValueButton;
import com.lucasmellof.forgeshot.screen.components.EnumConfigValueButton;
import com.lucasmellof.forgeshot.screen.components.IntegerConfigValueSlider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class SettingsScreen extends SettingsScreenBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForgeShot.MODID, "textures/gui/generic_4.png");

    public SettingsScreen() {
        super(Component.translatable("gui.forgeshot.settings.title"), TEXTURE, 248, 129);
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new IntegerConfigValueSlider(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 10,
                xSize - 20,
                20,
                Config.WIDTH,
                1,
		        15360 ,
                value -> Component.translatable("gui.forgeshot.settings.entry.width", value)));

        addRenderableWidget(new IntegerConfigValueSlider(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 10 + 25,
                xSize - 20,
                20,
                Config.HEIGHT,
                1,
		        8640,
                value -> Component.translatable("gui.forgeshot.settings.entry.height", value)));

        addRenderableWidget(new ConfigValueButton(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 10 + 25 *2,
                xSize - 20,
                20,
                Config.HIDE_HUD,
                value -> value
                        ? Component.translatable("gui.forgeshot.settings.entry.hide_hud.on")
                        : Component.translatable("gui.forgeshot.settings.entry.hide_hud.off")));

        addRenderableWidget(new ConfigValueButton(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 10 + 25 * 3,
                xSize - 20,
                20,
                Config.SCALE_HUD,
                value -> value
                        ? Component.translatable("gui.forgeshot.settings.entry.scale_hud.on")
                        : Component.translatable("gui.forgeshot.settings.entry.scale_hud.off")));
        addRenderableWidget(new EnumConfigValueButton<>(
		        guiLeft + 10,
		        guiTop + 7 + font.lineHeight + 10 + 25 * 4,
		        xSize - 20,
		        20,
		        Config.SAVE_FORMAT,
		        SaveFormats.class,
		        value -> Component.translatable("gui.forgeshot.settings.entry.save_format", value)));
    }
}
