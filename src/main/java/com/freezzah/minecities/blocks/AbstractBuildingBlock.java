package com.freezzah.minecities.blocks;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.entities.Inhabitant;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBuildingBlock extends Block implements IBuildingBlock {
    public AbstractBuildingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBreak(@NotNull Player player, @NotNull BlockPos pos) {
        if (player instanceof ServerPlayer serverPlayer) {
            City city = CityManager.getInstance().getCityByPlayer(Inhabitant.fromPlayer(serverPlayer));
            if (city != null) {
                CityManager.getInstance().removeBuilding(city, pos);
                return false;
            } else if(CityManager.getInstance().getCityByBuilding(pos) == null) {
                CityManager.getInstance().removeUnassociatedBuilding(pos);
            }
        }
        return true;
    }

    @Override
    public boolean onPlace(ServerPlayer serverPlayer, BlockPos pos){
        City city = CityManager.getInstance().getCityByPlayer(Inhabitant.fromPlayer(serverPlayer));
        if (city != null) {
            CityManager.getInstance().addBuilding(city, this, pos);
            return false;
        }
        return true;
    }

    public ResourceLocation getBuildingName() {
        return getBuildingType().getRegistryName();
    }
}
