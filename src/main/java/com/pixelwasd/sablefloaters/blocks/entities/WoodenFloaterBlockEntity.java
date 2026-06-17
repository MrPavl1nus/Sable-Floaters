package com.pixelwasd.sablefloaters.blocks.entities;

import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import com.pixelwasd.sablefloaters.Config;
import com.pixelwasd.sablefloaters.FloatersEntities;
import dev.ryanhcode.sable.Sable;
import dev.ryanhcode.sable.api.block.BlockEntitySubLevelActor;
import dev.ryanhcode.sable.api.physics.force.ForceGroups;
import dev.ryanhcode.sable.api.physics.force.QueuedForceGroup;
import dev.ryanhcode.sable.api.physics.handle.RigidBodyHandle;
import dev.ryanhcode.sable.physics.config.dimension_physics.DimensionPhysicsData;
import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class WoodenFloaterBlockEntity extends BlockEntity implements BlockEntitySubLevelActor {
    private static final float BUOYANCY_FORCE = 1; //TODO: реализовать в json для каждого поплавка
    // private static float maxLoad = 10; //TODO: реализовать в json для каждого поплавка

    public WoodenFloaterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public WoodenFloaterBlockEntity(BlockPos pos, BlockState blockState) {
        this(FloatersEntities.WOODEN_FLOATER_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    public void sable$physicsTick(ServerSubLevel subLevel, RigidBodyHandle handle, double timeStep) {  
        BlockEntitySubLevelActor.super.sable$physicsTick(subLevel, handle, timeStep);  
    
        final QueuedForceGroup forceGroup = subLevel.getOrCreateQueuedForceGroup(ForceGroups.LIFT.get());  
        
        final ServerLevel worldLevel = level.getServer().getLevel(level.dimension());
        
        final BlockPos localPos = this.getBlockPos();
        final Vector3d worldForcePoint = Sable.HELPER.projectOutOfSubLevel(worldLevel, new Vector3d(localPos.getX() + 0.5, localPos.getY() + 0.5, localPos.getZ() + 0.5));  

        final BlockPos worldBlockPos = new BlockPos((int)worldForcePoint.x, (int)worldForcePoint.y, (int)worldForcePoint.z);
        final Vector3dc worldActualCenter = Sable.HELPER.projectOutOfSubLevel(worldLevel,   
            new Vector3d(localPos.getX() + 0.5, localPos.getY() + 0.5, localPos.getZ() + 0.5));  
        
        final FluidState fluidState = worldLevel.getFluidState(worldBlockPos);        

        if (fluidState.isEmpty()) {  
            return; 
        }  
        
        //Buoyancy
        final float _maxLoad = (float)Config.FLOATER_MAX_LOAD.getAsDouble() / 2f; //idk, 1-unit per 2kpg
        // final float _maxLoad = WoodenFloaterBlockEntity.maxLoad / 2f; //idk, 1-unit per 2kpg
        final float load = Math.min((float)subLevel.getMassTracker().getMass(), _maxLoad);
        
        final float fluidLevel = fluidState.getHeight(worldLevel, worldBlockPos);
        final float waterLevelY = worldBlockPos.getY() + fluidLevel;  
        float depth = 0;
        
        //надеюсь это не повлияет сильно на производительность((
        if (fluidLevel == 1)
        {
            depth = 1;
        }
        else
        {
            depth = (float)Math.clamp(0, 1, waterLevelY - (worldActualCenter.y() + Config.DEPTH_OFFSET.get()));
        }

        final float gravityMagnitude = (float)DimensionPhysicsData.getGravity(worldLevel).length();
        final float buoyancyForce = (float)Math.abs(load * gravityMagnitude * depth * Config.GENERAL_FLOATERS_FORCE.get() * timeStep) * BUOYANCY_FORCE;

        //Total
        final float totalForce = buoyancyForce;

        //local to world
        final Quaterniond orientation = subLevel.logicalPose().orientation();
        
        final Vector3d worldForce = new Vector3d(0, totalForce, 0);
        final Vector3d localForce = orientation.transformInverse(worldForce, new Vector3d()).mul(totalForce);
    
        final Vector3d forcePoint = new Vector3d(localPos.getX()+ 0.5, localPos.getY()+ 0.5, localPos.getZ()+ 0.5);
        forceGroup.applyAndRecordPointForce(forcePoint, localForce);

        if (Config.LOGGING.get() == true)
        {
            Sable.LOGGER.debug("pos: " + worldActualCenter.toString());
            Sable.LOGGER.debug("force: " + Float.toString(totalForce));
            Sable.LOGGER.debug("depth: " + Float.toString(depth));
        }
    }
}