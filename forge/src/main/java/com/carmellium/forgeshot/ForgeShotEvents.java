package com.carmellium.forgeshot;

import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
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
		RegisterKeyMappingsEvent.BUS.addListener(ForgeShotEvents::onRegisterKeyBinding);
		TickEvent.ClientTickEvent.Pre.BUS.addListener(ForgeShotEvents::onTick);
	}
}
