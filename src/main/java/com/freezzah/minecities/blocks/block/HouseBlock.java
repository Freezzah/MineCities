package com.freezzah.minecities.blocks.block;

import com.freezzah.minecities.blocks.building.HouseBuilding;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.entities.Inhabitant;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class HouseBlock extends AbstractBuildingBlock {

    public HouseBlock(Properties properties) {
        super(properties);
    }

    public BuildingEntry getBuildingType() {
        return ModBuildingRegistry.HOUSE.get();
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHit);
            City city = CityManager.getInstance().getCityByBuilding(pPos);
            if (pPlayer.isCrouching()) {
                if (pLevel instanceof ServerLevel serverLevel) {
                    city.getBuildingManager().getBuildingByPos(pPos).increaseLevel(serverLevel, Inhabitant.fromPlayer(pPlayer));
                    return InteractionResult.SUCCESS_SERVER;
                }
            } else {
                IBuilding building = city.getBuildingManager().getBuildingByPos(pPos);
                if (building instanceof HouseBuilding houseBuilding) {
                    houseBuilding.addVillager();
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
}
