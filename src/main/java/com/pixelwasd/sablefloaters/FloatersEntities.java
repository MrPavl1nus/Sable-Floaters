package com.pixelwasd.sablefloaters;

import com.pixelwasd.sablefloaters.blocks.FloatersBlocks;
import com.pixelwasd.sablefloaters.blocks.entities.WoodenFloaterBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FloatersEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SableFloaters.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WoodenFloaterBlockEntity>> WOODEN_FLOATER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register("wooden_floater_block_entity", () -> BlockEntityType.Builder.of(WoodenFloaterBlockEntity::new, FloatersBlocks.WOODEN_FLOATER.get()).build(null));

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
