package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.City;
import net.minecraft.core.BlockPos;

public abstract class AbstractBuilding implements IBuilding {
    private final City city;
    private final BlockPos blockPos;

    public AbstractBuilding(City city, BlockPos blockPos) {
        this.city = city;
        this.blockPos = blockPos;
    }

    @Override
    public BlockPos getBlockPos() {
        return blockPos;
    }
}
