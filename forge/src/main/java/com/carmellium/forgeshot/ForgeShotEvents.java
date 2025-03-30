package com.carmellium.forgeshot;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 3/28/25
 */
public class ForgeShotEvents {
	static void onRegisterKeyBinding(RegisterKeyMappingsEvent e) {
		ForgeShotClient.INSTANCE.onRegisterBinding(e::register);
	}

	static void onTick(TickEvent.ClientTickEvent.Pre e) {
		ForgeShotClient.INSTANCE.onTick();
	}

	public static void init() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(ForgeShotEvents::onRegisterKeyBinding);
		MinecraftForge.EVENT_BUS.addListener(ForgeShotEvents::onTick);
	}
}
