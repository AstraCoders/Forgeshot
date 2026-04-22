package com.carmellium.forgeshot.screen;

import com.carmellium.forgeshot.CommonResolutions;
import com.carmellium.forgeshot.Constants;
import com.carmellium.forgeshot.config.SaveFormats;
import com.carmellium.forgeshot.platform.Services;
import com.carmellium.forgeshot.screen.widgets.ConfigValueButton;
import com.carmellium.forgeshot.screen.widgets.CustomResButton;
import com.carmellium.forgeshot.screen.widgets.EnumConfigValueButton;
import com.carmellium.forgeshot.screen.widgets.ResolutionSlider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class SettingsScreen extends SettingsScreenBase {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/generic_4.png");

    public SettingsScreen() {
        super(Component.translatable("gui.forgeshot.settings.title"), TEXTURE, 248, 180);
    }

    private CustomResButton resButton;
    private ResolutionSlider width;
    private ResolutionSlider height;
    boolean first = false;

    @Override
    protected void init() {
        super.init();
        this.resButton = addRenderableWidget(new CustomResButton(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 4 + 25 * 2,
                xSize - 20,
                20,
                CommonResolutions.getRes(Services.PLATFORM.getWidth().get(), Services.PLATFORM.getHeight().get()),
                value -> {
                    Services.PLATFORM.getWidth().set(value.getWidth());
                    Services.PLATFORM.getWidth().save();
                    Services.PLATFORM.getHeight().set(value.getHeight());
                    Services.PLATFORM.getHeight().save();
                    if (width != null) {
                        width.refetch();
                    }
                    if (height != null) {
                        height.refetch();
                    }
                    first = true;
                    return Component.translatable("gui.forgeshot.settings.entry.res_button", value.getName());
                }));

        width = addRenderableWidget(new ResolutionSlider(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 4,
                xSize - 20,
                20,
                Services.PLATFORM.getWidth(),
                1,
                15360,
                value -> Component.translatable("gui.forgeshot.settings.entry.width", value)));

        height = addRenderableWidget(new ResolutionSlider(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 4 + 25,
                xSize - 20,
                20,
                Services.PLATFORM.getHeight(),
                1,
                8640,
                value -> Component.translatable("gui.forgeshot.settings.entry.height", value)));

        addRenderableWidget(new ConfigValueButton(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 4 + 25 * 3,
                xSize / 2 - 12,
                20,
                Services.PLATFORM.shouldHideHud(),
                value -> value
                        ? Component.translatable("gui.forgeshot.settings.entry.hide_hud.on")
                        : Component.translatable("gui.forgeshot.settings.entry.hide_hud.off")));

        addRenderableWidget(new ConfigValueButton(
                guiLeft + xSize / 2 + 2,
                guiTop + 7 + font.lineHeight + 4 + 25 * 3,
                xSize / 2 - 12,
                20,
                Services.PLATFORM.shouldScaleHud(),
                value -> value
                        ? Component.translatable("gui.forgeshot.settings.entry.scale_hud.on")
                        : Component.translatable("gui.forgeshot.settings.entry.scale_hud.off")));
        addRenderableWidget(new EnumConfigValueButton<>(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 4 + 25 * 4,
                xSize - 20,
                20,
                Services.PLATFORM.getSaveFormat(),
                SaveFormats.class,
                value -> Component.translatable("gui.forgeshot.settings.entry.save_format", value.toString())));
    }

    public CustomResButton getResButton() {
        return resButton;
    }

    public ResolutionSlider getWidth() {
        return width;
    }

    public ResolutionSlider getHeight() {
        return height;
    }
}
