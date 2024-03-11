package com.freezzah.minecities.utils;

import net.minecraft.core.BlockPos;

public class BlockPosHelper {
    public static boolean equals(BlockPos a, BlockPos b){
        return a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
    }
}
