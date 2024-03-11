package com.freezzah.minecities.blocks.building;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public interface IBuildingBlock {

    <T extends IBuilding> Class<T> getType();

    boolean onBreak(@NotNull Player player, @NotNull BlockPos pos);
}
