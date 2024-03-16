package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.utils.BlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

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

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag = BlockPosHelper.writeBlockPos(tag, blockPos);
        return tag;
    }

    public static AbstractBuilding read(CompoundTag tag){

    }
}
