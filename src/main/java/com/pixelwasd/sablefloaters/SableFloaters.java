package com.pixelwasd.sablefloaters;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.pixelwasd.sablefloaters.Items.FloatersItems;
import com.pixelwasd.sablefloaters.blocks.FloatersBlocks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SableFloaters.MODID)
public class SableFloaters {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "sablefloaters";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public SableFloaters(IEventBus modEventBus, ModContainer modContainer) {
        SableFloatersCreativeTabs.register(modEventBus);
        FloatersBlocks.register(modEventBus);
        FloatersItems.register(modEventBus);
        FloatersEntities.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        // {
        //     event.accept(FloatersBlocks.WOODEN_FLOATER);
        //     event.accept(FloatersBlocks.WOODEN_FLOATER_BUNDLE);
        // }
    }
}
