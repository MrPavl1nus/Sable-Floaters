package com.pixelwasd.sablefloaters.blocks;

import java.util.function.Supplier;

import com.pixelwasd.sablefloaters.SableFloaters;
import com.pixelwasd.sablefloaters.Items.FloatersItems;
import com.pixelwasd.sablefloaters.blocks.floaters.BaseFloaterBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FloatersBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SableFloaters.MODID);

    public static final DeferredBlock<Block> WOODEN_FLOATER = registerBlock("wooden_floater", () -> new BaseFloaterBlock(Properties.of()
        .strength(.8f).sound(SoundType.BAMBOO_WOOD).noLootTable()
    ,1f, 5f),
        new Item.Properties().stacksTo(16));

        public static final DeferredBlock<Block> WOODEN_FLOATER_BUNDLE = registerBlock("wooden_floater_bundle", () -> new BaseFloaterBlock(Properties.of()
        .strength(1f).sound(SoundType.LANTERN)
    ,0.95f, 20f),
        new Item.Properties().stacksTo(8));

        public static final DeferredBlock<Block> REINFORCED_WOODEN_FLOATER = registerBlock("reinforced_wooden_floater", () -> new BaseFloaterBlock(Properties.of()
        .strength(1.3f).sound(SoundType.LANTERN)
    ,0.95f, 5f),
        new Item.Properties().stacksTo(16));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block, Item.Properties itemProperties)
    {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, itemProperties);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block, Item.Properties properties){
        FloatersItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
