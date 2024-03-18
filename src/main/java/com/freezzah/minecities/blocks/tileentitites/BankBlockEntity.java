package com.freezzah.minecities.blocks.tileentitites;

import com.freezzah.minecities.blocks.AbstractBuildingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BankBlockEntity extends AbstractBlockEntity {
    public BankBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
}
