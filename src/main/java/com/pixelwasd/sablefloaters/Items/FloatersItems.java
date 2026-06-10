package com.pixelwasd.sablefloaters.Items;

import com.pixelwasd.sablefloaters.SableFloaters;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FloatersItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SableFloaters.MODID);

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}