package com.freezzah.minecities.blocks.block;

import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.EntityBlock;
import org.jetbrains.annotations.NotNull;

public interface IBuildingBlock extends EntityBlock {
    /**
     * Should be called from events.
     * Returns true if event should be cancelled.
     *
     * @param player player
     * @param pos    position
     * @return true if cancel, false if continue
     */
    boolean onBreak(@NotNull Player player, @NotNull BlockPos pos);

    /**
     * Should be called from events.
     * Returns true if event should be cancelled.
     *
     * @param player player
     * @param pos    position
     * @return true if cancel, false if continue
     */
    boolean onPlace(ServerPlayer player, BlockPos pos);

    /**
     * Method to return building type
     *
     * @return {@link BuildingEntry}
     */
    BuildingEntry getBuildingType();

    /**
     * Return the name of the building as a {@link ResourceLocation}
     *
     * @return {@link ResourceLocation}
     */
    ResourceLocation getBuildingName();
}
