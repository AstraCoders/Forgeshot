package com.lucasmellof.forgeshot;

import com.lucasmellof.forgeshot.screen.SettingsScreen;
import com.lucasmellof.forgeshot.tasks.CaptureTask;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 09/09/2023
 */
public class ForgeShotClient {
    public static final ForgeShotClient INSTANCE = new ForgeShotClient();
    private CaptureTask task;

    private static final KeyMapping SCREENSHOT_KEY = new KeyMapping(
            "key.forgeshot.screenshot", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F4, "key.categories.misc");
    private static final KeyMapping SCREENSHOT_GUI_KEY = new KeyMapping(
            "key.forgeshot.screenshot_gui", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F6, "key.categories.misc");

    public static void init() {
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(INSTANCE::onRegisterBinding);

        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }

    public void onRegisterBinding(RegisterKeyMappingsEvent event) {
        event.register(SCREENSHOT_KEY);
        event.register(SCREENSHOT_GUI_KEY);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (SCREENSHOT_KEY.consumeClick()) {
            capture();
        }
        if (SCREENSHOT_GUI_KEY.consumeClick()) {
            Minecraft.getInstance().setScreen(new SettingsScreen());
        }
    }

    public void capture() {
        if (task == null) {
            task = new CaptureTask(Mine.getScreenshotPath());
        }
    }

    public void onRender() {
        if (task != null && task.onRender()) {
            task = null;
        }
    }

    public float getScale() {
        if (task == null) {
            return 1;
        }
        return task.getScale();
    }
}
