package com.carmellium.forgeshot;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
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

	public static void init(FMLJavaModLoadingContext ctx) {
		var modBus = ctx.getModBusGroup();
		RegisterKeyMappingsEvent.getBus(modBus).addListener(ForgeShotEvents::onRegisterKeyBinding);
		TickEvent.ClientTickEvent.Pre.BUS.addListener(ForgeShotEvents::onTick);
	}
}
