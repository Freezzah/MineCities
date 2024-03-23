package com.freezzah.minecities.blocks;

import com.freezzah.minecities.blocks.blockentities.WaterCollectorBlockEntity;
import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.blocks.registry.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaterCollectorBlock extends AbstractBuildingBlock{
    public WaterCollectorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BuildingEntry getBuildingType() {
        return ModBuildingRegistry.WATER_COLLECTOR.get();
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        if(pLevel instanceof ServerLevel) {
            return pBlockEntityType == ModBlockEntity.WATER_COLLECTOR_BLOCK_ENTITY.get() ? WaterCollectorBlockEntity::tick : null;
        }
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new WaterCollectorBlockEntity(blockPos, blockState);
    }
}
