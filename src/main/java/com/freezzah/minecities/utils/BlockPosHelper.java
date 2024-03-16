package com.freezzah.minecities.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.checkerframework.checker.units.qual.C;

public class BlockPosHelper {
    private static final String TAG = "blockpos";
    public static boolean equals(BlockPos a, BlockPos b){
        return a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
    }

    public static CompoundTag writeBlockPos(CompoundTag tag, BlockPos pos){
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("x", pos.getX());
        compoundTag.putInt("y", pos.getY());
        compoundTag.putInt("z", pos.getZ());
        tag.put(TAG, compoundTag);
        return tag;
    }

    public static BlockPos readBlockPos(CompoundTag tag){
        CompoundTag compoundTag = tag.getCompound(TAG);
        return new BlockPos(compoundTag.getInt("x"), compoundTag.getInt("y"), compoundTag.getInt("z"));
    }
}
