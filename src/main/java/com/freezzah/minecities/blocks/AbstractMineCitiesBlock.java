package com.freezzah.minecities.blocks;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.entities.Inhabitant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMineCitiesBlock extends Block implements IMineCitiesBlock{
    public AbstractMineCitiesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBreak(@NotNull Player player, @NotNull BlockPos pos) {
        if (player instanceof ServerPlayer serverPlayer) {
            City city = CityManager.getInstance().getCityByPlayer(Inhabitant.fromPlayer(serverPlayer));
            if (city != null) {
                CityManager.getInstance().removeBuilding(city, this);
                return false;

            }
        }
        return true;
    }

}
