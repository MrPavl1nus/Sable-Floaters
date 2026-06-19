package com.pixelwasd.sablefloaters;

import java.util.function.Supplier;

import com.pixelwasd.sablefloaters.blocks.FloatersBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SableFloatersCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> FLOATERS_CREATIVE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, SableFloaters.MODID);

    public static final Supplier<CreativeModeTab> FLOATERS_CREATIVE_TAB = FLOATERS_CREATIVE_TABS.register(
            "floaters_creative_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(FloatersBlocks.WOODEN_FLOATER.get()))
                    .title(Component.translatable("creativetab.sablefloaters.floaters_creative_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(FloatersBlocks.WOODEN_FLOATER);
                        output.accept(FloatersBlocks.WOODEN_FLOATER_BUNDLE);
                        output.accept(FloatersBlocks.REINFORCED_WOODEN_FLOATER);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        FLOATERS_CREATIVE_TABS.register(eventBus);
    }
}
