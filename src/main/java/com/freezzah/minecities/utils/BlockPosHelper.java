package com.freezzah.minecities.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class BlockPosHelper {
    private static final String TAG = "blockpos";

    /**
     * Validates if two block posses are equal in terms of coordinates.
     * @param blockPosA first {@link BlockPos} to compare
     * @param blockPosB second {@link BlockPos} to compare
     * @return true if equal, otherwise false
     */
    public static boolean equals(BlockPos blockPosA, BlockPos blockPosB){
        return blockPosA.getX() == blockPosB.getX() &&
                blockPosA.getY() == blockPosB.getY() &&
                blockPosA.getZ() == blockPosB.getZ();
    }

    /**
     * Writes {@link BlockPos} to a new NBT Tag, and adds the tag to the given parameter
     * @param tag {@link CompoundTag} which will hold the {@link BlockPos} in a new {@link CompoundTag}
     * @param pos {@link BlockPos} to write to the NBT.
     * @return {@link CompoundTag} with an new {@link CompoundTag} entry with the {@link BlockPos}
     */
    public static CompoundTag writeBlockPos(CompoundTag tag, BlockPos pos){
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("x", pos.getX());
        compoundTag.putInt("y", pos.getY());
        compoundTag.putInt("z", pos.getZ());
        tag.put(TAG, compoundTag);
        return tag;
    }
    /**
     * Reads {@link BlockPos} from an NBT Tag.
     * @param tag {@link CompoundTag} which holds the {@link BlockPos} in a new {@link CompoundTag}
     * @return {@link BlockPos} which was in the {@link CompoundTag}
     */
    public static BlockPos readBlockPos(CompoundTag tag){
        CompoundTag compoundTag = tag.getCompound(TAG);
        return new BlockPos(compoundTag.getInt("x"), compoundTag.getInt("y"), compoundTag.getInt("z"));
    }
}
