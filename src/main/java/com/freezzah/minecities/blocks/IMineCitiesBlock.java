package com.freezzah.minecities.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public interface IMineCitiesBlock {
    /**
     * Should be called from events.
     * Returns true if event should be cancelled.
     * @param player
     * @param pos
     * @return
     */
    boolean onBreak(@NotNull Player player, @NotNull BlockPos pos);
}
