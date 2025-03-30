package com.carmellium.forgeshot;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;

/*
 * @author Lucasmellof, Lucas de Mello Freitas created on 3/28/25
 */
public class NeoForgeShotEvents {
	static void onRegisterKeyBinding(RegisterKeyMappingsEvent e) {
		ForgeShotClient.INSTANCE.onRegisterBinding(e::register);
	}

	static void onTick(ClientTickEvent.Pre e) {
		ForgeShotClient.INSTANCE.onTick();
	}


	public static void init(IEventBus bus) {
		bus.addListener(NeoForgeShotEvents::onRegisterKeyBinding);
		NeoForge.EVENT_BUS.addListener(NeoForgeShotEvents::onTick);
	}
}
