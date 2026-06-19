package com.pixelwasd.sablefloaters.blocks.floaters;

import com.pixelwasd.sablefloaters.blocks.entities.BaseFloaterBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BaseFloaterBlock extends Block implements EntityBlock {
    private final float BUOYANCY;
    private final float MAX_LOAD;

    public BaseFloaterBlock(Properties properties, float buoyancy, float maxLoad) {
        super(properties);
        BUOYANCY = buoyancy;
        MAX_LOAD = maxLoad;
    }

    public float getBuoyancy() {
        return BUOYANCY;
    }

    public float getMaxLoad() {
        return MAX_LOAD / 2;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BaseFloaterBlockEntity(pos, state);
    }
}
