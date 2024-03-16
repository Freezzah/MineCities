package com.freezzah.minecities.blocks.building;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public interface IBuildingBlock {

    boolean onBreak(@NotNull Player player, @NotNull BlockPos pos);
    ResourceLocation getBuildingName();
}
