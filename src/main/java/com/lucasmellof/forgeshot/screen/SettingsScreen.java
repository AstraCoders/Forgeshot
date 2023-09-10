package com.lucasmellof.forgeshot.screen;

import com.lucasmellof.forgeshot.CommonRes;
import com.lucasmellof.forgeshot.ForgeShot;
import com.lucasmellof.forgeshot.config.Config;
import com.lucasmellof.forgeshot.config.SaveFormats;
import com.lucasmellof.forgeshot.screen.components.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class SettingsScreen extends SettingsScreenBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForgeShot.MODID, "textures/gui/generic_4.png");

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
                CommonRes.getRes(Config.WIDTH.get(), Config.HEIGHT.get()),
                value -> {
                    Config.WIDTH.set(value.getWidth());
                    Config.WIDTH.save();
                    Config.HEIGHT.set(value.getHeight());
                    Config.HEIGHT.save();
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
                Config.WIDTH,
                1,
                15360,
                value -> Component.translatable("gui.forgeshot.settings.entry.width", value)));

        height = addRenderableWidget(new ResolutionSlider(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 4 + 25,
                xSize - 20,
                20,
                Config.HEIGHT,
                1,
                8640,
                value -> Component.translatable("gui.forgeshot.settings.entry.height", value)));

        addRenderableWidget(new ConfigValueButton(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 4 + 25 * 3,
                xSize / 2 - 12,
                20,
                Config.HIDE_HUD,
                value -> value
                        ? Component.translatable("gui.forgeshot.settings.entry.hide_hud.on")
                        : Component.translatable("gui.forgeshot.settings.entry.hide_hud.off")));

        addRenderableWidget(new ConfigValueButton(
                guiLeft + xSize / 2 + 2,
                guiTop + 7 + font.lineHeight + 4 + 25 * 3,
                xSize / 2 - 12,
                20,
                Config.SCALE_HUD,
                value -> value
                        ? Component.translatable("gui.forgeshot.settings.entry.scale_hud.on")
                        : Component.translatable("gui.forgeshot.settings.entry.scale_hud.off")));
        addRenderableWidget(new EnumConfigValueButton<>(
                guiLeft + 10,
                guiTop + 7 + font.lineHeight + 4 + 25 * 4,
                xSize - 20,
                20,
                Config.SAVE_FORMAT,
                SaveFormats.class,
                value -> Component.translatable("gui.forgeshot.settings.entry.save_format", value)));
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
