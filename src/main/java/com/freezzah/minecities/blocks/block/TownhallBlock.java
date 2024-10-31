package com.freezzah.minecities.blocks.block;

import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.client.gui.menu.CityOverviewMenu;
import com.freezzah.minecities.entities.Inhabitant;
import com.freezzah.minecities.utils.FriendlyByteBufHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TownhallBlock extends AbstractBuildingBlock implements IBuildingBlock {
    public TownhallBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pLevel instanceof ServerLevel serverLevel) {
                City city = CityManager.getInstance().getCityByBuilding(pPos);
                if (city != null) {
                    if (pPlayer.isCrouching()) {
                        IBuilding building = city.getBuildingManager().getBuildingByPos(pPos);
                        if (building == null) {
                            return InteractionResult.FAIL;
                        }
                        building.increaseLevel(serverLevel, Inhabitant.fromPlayer(pPlayer));
                        return InteractionResult.SUCCESS;
                    }
                    MenuProvider menuProvider = pState.getMenuProvider(pLevel, pPos);
                    if (menuProvider == null) {
                        return InteractionResult.FAIL;
                    }
                    pPlayer.openMenu(menuProvider, new FriendlyByteBufHelper(city.getId())::writeUUID);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        City city = CityManager.getInstance().getCityByBuilding(pPos);
        if (city == null) {
            return null;
        }
        return new SimpleMenuProvider(
                (pContainerId, pPlayerInventory, pPlayer) -> new CityOverviewMenu(pContainerId, pPlayerInventory, city.getId()),
                Component.literal("mymenu"));
    }

    @Override
    public boolean onBreak(@NotNull Player player, @NotNull BlockPos pos) {
        if (player instanceof ServerPlayer serverPlayer) {
            City city = CityManager.getInstance().getCityByPlayer(Inhabitant.fromPlayer(serverPlayer));
            if (city != null) {
                if (city.isOwner(Inhabitant.fromPlayer(player))) {
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
        if (city == null) {
            return true;
        }
        city.getBuildingManager().addBuilding(pos, this);
        return false;
    }

    @Override
    public BuildingEntry getBuildingType() {
        return ModBuildingRegistry.TOWNHALL.get();
    }
}
