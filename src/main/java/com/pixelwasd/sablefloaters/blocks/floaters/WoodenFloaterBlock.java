package com.pixelwasd.sablefloaters.blocks.floaters;

import com.pixelwasd.sablefloaters.blocks.entities.WoodenFloaterBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WoodenFloaterBlock extends Block implements EntityBlock{

    public WoodenFloaterBlock(Properties properties) {
        super(properties);
        
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WoodenFloaterBlockEntity(pos, state);
    }
}
