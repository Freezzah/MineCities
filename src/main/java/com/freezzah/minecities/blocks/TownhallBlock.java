package com.freezzah.minecities.blocks;

import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.IBuildingBlock;
import com.freezzah.minecities.blocks.building.TownhallBuilding;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.entities.Inhabitant;
import com.freezzah.minecities.utils.BlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class TownhallBlock extends AbstractMineCitiesBlock implements IBuildingBlock {
    public TownhallBlock(Properties properties) {
        super(properties);
    }

    @Override
    public <T extends IBuilding> Class<T> getType(){
        return (Class<T>) TownhallBuilding.class;
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
                TownhallBuilding townhallBuilding = city.getTownhall();
                if (BlockPosHelper.equals(townhallBuilding.getBlockPos(), pos)) {
                    CityManager.getInstance().destroyCity(city);
                    return false;
                }

            }
        }
        return true;
    }
}
