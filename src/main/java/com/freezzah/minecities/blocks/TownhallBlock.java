package com.freezzah.minecities.blocks;

import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.entities.Inhabitant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class TownhallBlock extends AbstractBuildingBlock implements IBuildingBlock {
    public TownhallBlock(Properties properties) {
        super(properties);
    }


    @Override
    public boolean onBreak(@NotNull Player player, @NotNull BlockPos pos) {
        if (player instanceof ServerPlayer serverPlayer) {
            City city = CityManager.getInstance().getCityByPlayer(Inhabitant.fromPlayer(serverPlayer));
            if (city != null) {
                if(city.isOwner(Inhabitant.fromPlayer(player))) {
                    CityManager.getInstance().destroyCity(city);
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public boolean onPlace(ServerPlayer player, BlockPos pos) {
        City city = CityManager.getInstance().getCityByPlayer(Inhabitant.fromPlayer(player));
        if (city != null) {
            return true;
        }
        city = CityManager.getInstance().createCity(Inhabitant.fromPlayer(player));
        CityManager.getInstance().addBuilding(city, this, pos);
        return false;
    }

    @Override
    public BuildingEntry getBuildingType(){
        return ModBuildingRegistry.townhall.get();
    }
}
