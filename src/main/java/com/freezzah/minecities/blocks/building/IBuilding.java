package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.utils.ITaggable;
import net.minecraft.core.BlockPos;

public interface IBuilding extends ITaggable {

    BlockPos getBlockPos();
}
