package com.freezzah.minecities.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class BlockPosHelper {
    private static final String TAG = "blockpos";

    /**
     * Validates if two block posses are equal in terms of coordinates.
     *
     * @param blockPosA first {@link BlockPos} to compare
     * @param blockPosB second {@link BlockPos} to compare
     * @return true if equal, otherwise false
     */
    public static boolean equals(BlockPos blockPosA, BlockPos blockPosB) {
        return blockPosA.getX() == blockPosB.getX() &&
                blockPosA.getY() == blockPosB.getY() &&
                blockPosA.getZ() == blockPosB.getZ();
    }
    /**
     * Writes {@link BlockPos} to a new NBT Tag, and adds the tag to the given parameter
     *
     * @param tag {@link CompoundTag} which will hold the {@link BlockPos} in a new {@link CompoundTag}
     * @param pos {@link BlockPos} to write to the NBT.
     * @return {@link CompoundTag} with an new {@link CompoundTag} entry with the {@link BlockPos}
     */
    public static CompoundTag writeBlockPos(CompoundTag tag, BlockPos pos) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("x", pos.getX());
        compoundTag.putInt("y", pos.getY());
        compoundTag.putInt("z", pos.getZ());
        tag.put(TAG, compoundTag);
        return tag;
    }
    /**
     * Reads {@link BlockPos} from an NBT Tag.
     *
     * @param tag {@link CompoundTag} which holds the {@link BlockPos} in a new {@link CompoundTag}
     * @return {@link BlockPos} which was in the {@link CompoundTag}
     */
    public static BlockPos readBlockPos(CompoundTag tag) {
        CompoundTag compoundTag = tag.getCompound(TAG);
        return new BlockPos(compoundTag.getInt("x"), compoundTag.getInt("y"), compoundTag.getInt("z"));
    }
    /**
     * Calculates the distance between two {@link BlockPos} in 3D.
     * @param blockPos1 first block
     * @param blockPos2 second block
     * @return distance between blocks.
     */
    public static double distance(BlockPos blockPos1, BlockPos blockPos2){
        double x = Math.pow(Math.abs(blockPos1.getX() - blockPos2.getX()), 2);
        double y = Math.pow(Math.abs(blockPos1.getY() - blockPos2.getY()), 2);
        double z = Math.pow(Math.abs(blockPos1.getZ() - blockPos2.getZ()), 2);
        return Math.sqrt(x + y + z);
    }
}
