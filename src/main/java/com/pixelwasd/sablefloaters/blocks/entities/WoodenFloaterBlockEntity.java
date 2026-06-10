package com.pixelwasd.sablefloaters.blocks.entities;

import org.joml.Vector3d;
import org.joml.Vector3dc;

import com.pixelwasd.sablefloaters.Config;
import com.pixelwasd.sablefloaters.FloatersEntities;
import dev.ryanhcode.sable.api.block.BlockEntitySubLevelActor;
import dev.ryanhcode.sable.api.physics.force.ForceGroups;
import dev.ryanhcode.sable.api.physics.force.QueuedForceGroup;
import dev.ryanhcode.sable.api.physics.handle.RigidBodyHandle;
import dev.ryanhcode.sable.physics.chunk.VoxelNeighborhoodState;
import dev.ryanhcode.sable.physics.config.dimension_physics.DimensionPhysicsData;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class WoodenFloaterBlockEntity extends BlockEntity implements BlockEntitySubLevelActor {
    private static final double BUOYANCY_FORCE = 1;

    public WoodenFloaterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public WoodenFloaterBlockEntity(BlockPos pos, BlockState blockState) {
        this(FloatersEntities.WOODEN_FLOATER_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    public void sable$physicsTick(ServerSubLevel subLevel, RigidBodyHandle handle, double timeStep) {
        BlockEntitySubLevelActor.super.sable$physicsTick(subLevel, handle, timeStep);

       QueuedForceGroup forceGroup = subLevel.getOrCreateQueuedForceGroup(ForceGroups.LIFT.get());

       BlockState blockState = level.getBlockState(worldPosition);
       boolean isLiquid = VoxelNeighborhoodState.isLiquid(blockState);

       if (!isLiquid) return;

       FluidState fluidState = level.getFluidState(worldPosition);
       final double fluidLevelY = (float) worldPosition.getY() + fluidState.getHeight(level, worldPosition);

       double blockMinY = blockState.getShape(level, worldPosition).min(Axis.Y);
       double depth = Math.max(fluidLevelY - blockMinY, 0.0);
       double force = 1000 * DimensionPhysicsData.getGravity(level).y * depth;

        Vector3d worldUp = new Vector3d(0, 1, 0);
        Vector3d localForce = new Vector3d(worldUp);
        localForce.mul(BUOYANCY_FORCE);

        Vector3dc forcePoint = new Vector3d(
            this.worldPosition.getX() + 0.5,
            this.worldPosition.getY() + 0.5,
            this.worldPosition.getZ() + 0.5
        );

        forceGroup.applyAndRecordPointForce(forcePoint, localForce.mul(force).mul(Config.FLOATER_FORCE.get()));
    }
}