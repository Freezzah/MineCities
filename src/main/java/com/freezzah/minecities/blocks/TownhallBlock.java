package com.freezzah.minecities.blocks;

import com.freezzah.minecities.blocks.building.*;
import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuilding;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.entities.Inhabitant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class TownhallBlock extends AbstractMineCitiesBlock implements IBuildingBlock {
    public TownhallBlock(Properties properties) {
        super(properties);
    }

    /**
     * Should be called from events.
     * Returns true if event should be cancelled.
     * @param player
     * @param pos
     * @return
     */
    @Override
    public boolean onBreak(@NotNull Player player, @NotNull BlockPos pos) {
        if (player instanceof ServerPlayer serverPlayer) {
            City city = CityManager.getInstance().getCityByPlayer(Inhabitant.fromPlayer(serverPlayer));
            if (city != null) {
                CityManager.getInstance().destroyCity(city);
                return false;

            }
        }
        return true;
    }

    @Override
    public BuildingEntry getBuildingType(){
        return ModBuilding.townhall.get();
    }
}
