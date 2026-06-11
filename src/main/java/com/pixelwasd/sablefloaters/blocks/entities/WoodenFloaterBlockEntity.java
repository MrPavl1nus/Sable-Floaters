package com.pixelwasd.sablefloaters.blocks.entities;

import org.checkerframework.checker.units.qual.t;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import com.pixelwasd.sablefloaters.Config;
import com.pixelwasd.sablefloaters.FloatersEntities;
import com.pixelwasd.sablefloaters.SableFloaters;

import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.block.BlockEntitySubLevelActor;
import dev.ryanhcode.sable.api.physics.force.ForceGroups;
import dev.ryanhcode.sable.api.physics.force.QueuedForceGroup;
import dev.ryanhcode.sable.api.physics.handle.RigidBodyHandle;
import dev.ryanhcode.sable.physics.chunk.VoxelNeighborhoodState;
import dev.ryanhcode.sable.physics.config.PhysicsConfigData;
import dev.ryanhcode.sable.physics.config.dimension_physics.DimensionPhysicsData;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class WoodenFloaterBlockEntity extends BlockEntity implements BlockEntitySubLevelActor {
    private static final double BUOYANCY_FORCE = 10;

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
        
        ServerLevel worldLevel = level.getServer().getLevel(level.dimension());
        
        BlockPos localPos = this.getBlockPos();
        Vector3d worldForcePoint = Sable.HELPER.projectOutOfSubLevel(worldLevel, new Vector3d(localPos.getX() + 0.5, localPos.getY() + 0.5, localPos.getZ() + 0.5));  

        BlockPos worldBlockPos = new BlockPos((int)worldForcePoint.x, (int)worldForcePoint.y, (int)worldForcePoint.z);
        
        BlockState blockState = worldLevel.getBlockState(localPos);  
        FluidState fluidState = worldLevel.getFluidState(worldBlockPos);  
        
        if (fluidState.isEmpty()) {  
            return; 
        }  
        
        double waterLevelY = worldBlockPos.getY() + fluidState.getHeight(worldLevel, worldBlockPos);
        double depth = Math.max(waterLevelY - worldBlockPos.getY(), 0);
        double floatForce = Math.abs(DimensionPhysicsData.getGravity(worldLevel).y) * depth * BUOYANCY_FORCE * Config.GENERAL_FLOATERS_FORCE.get();

        Quaterniond orientation = subLevel.logicalPose().orientation();

        Vector3d forceDir = new Vector3d(0, 1, 0);
        Vector3d localForce = orientation.transformInverse(forceDir).mul(floatForce);

        Vector3dc centerOfMass = subLevel.getMassTracker().getCenterOfMass();
        // double roll = Math.acos(centerOfMass.normalize(new Vector3d()).dot(new Vector3d(1, 0, 0)));
        // double tilt = Math.acos(centerOfMass.normalize(new Vector3d()).dot(new Vector3d(0, 1, 0)));

        // forceGroup.applyAndRecordPointForce(worldForcePoint, force);
        forceGroup.applyAndRecordPointForce(new Vector3d(localPos.getX()+ 0.5, localPos.getY()+ 0.5, localPos.getZ()+ 0.5), localForce);
    }
}