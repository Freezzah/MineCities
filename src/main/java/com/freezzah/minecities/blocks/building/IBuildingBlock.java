package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public interface IBuildingBlock {
    ResourceLocation getBuildingName();
    /**
     * Should be called from events.
     * Returns true if event should be cancelled.
     * @param player
     * @param pos
     * @return
     */
    boolean onBreak(@NotNull Player player, @NotNull BlockPos pos);
    BuildingEntry getBuildingType();
}
