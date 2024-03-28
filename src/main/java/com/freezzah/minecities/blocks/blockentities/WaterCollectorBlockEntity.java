package com.freezzah.minecities.blocks.blockentities;

import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.WaterCollectorBuilding;
import com.freezzah.minecities.blocks.registry.ModBlockEntity;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.WaterFluid;
import org.jetbrains.annotations.NotNull;

public class WaterCollectorBlockEntity extends AbstractBlockEntity {
    private final BlockPos blockPos;

    public WaterCollectorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntity.WATER_COLLECTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.blockPos = pPos;
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (t instanceof WaterCollectorBlockEntity waterCollectorBlockEntity) {
            if (level.getGameTime() % 100 == 0) {
                waterCollectorBlockEntity.collectWater(level);
            }
        }
    }

    private void collectWater(@NotNull Level level) {
        if (!level.isClientSide) {
            City city = CityManager.getInstance().getCityByBuilding(blockPos);
            if (city == null)
                return;
            IBuilding building = city.getBuildingManager().getBuildingByPos(blockPos);
            if (building instanceof WaterCollectorBuilding waterCollectorBuilding) {
                int sides = 0;
                for (Direction direction : Direction.values()) {
                    Block block = level.getBlockState(blockPos.relative(direction)).getBlock();
                    if (block instanceof LiquidBlock liquidBlock) {
                        if (liquidBlock.getFluid() instanceof WaterFluid) {
                            sides++;
                        }
                    }
                }
                waterCollectorBuilding.collectWater(sides * getMultiplier());
            }
        }
    }

    private int getMultiplier() {
        return 20;
    }
}