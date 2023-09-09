package com.lucasmellof.forgeshot;

import com.lucasmellof.forgeshot.config.Config;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ForgeShot.MODID)
public class ForgeShot {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "forgeshot";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ForgeShot() {
        // Client side mod so register extension point for client side mods
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> "ANY", (remote, isServer) -> true));
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.GENERAL_SPEC);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ForgeShotClient::init);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
