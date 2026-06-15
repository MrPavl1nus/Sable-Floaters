package com.pixelwasd.sablefloaters.blocks;

import java.util.function.Supplier;

import com.pixelwasd.sablefloaters.SableFloaters;
import com.pixelwasd.sablefloaters.Items.FloatersItems;
import com.pixelwasd.sablefloaters.blocks.floaters.WoodenFloaterBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FloatersBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SableFloaters.MODID);

    public static final DeferredBlock<Block> WOODEN_FLOATER = registerBlock("wooden_floater", () -> new WoodenFloaterBlock(BlockBehaviour.Properties.of()
    .strength(1.4f).sound(SoundType.BAMBOO).noLootTable()), new Item.Properties().stacksTo(16));  

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
