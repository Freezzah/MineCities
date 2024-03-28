package com.freezzah.minecities.blocks;

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
import net.minecraft.world.InteractionHand;
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
    public @NotNull InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pLevel instanceof ServerLevel serverLevel) {
                City city = CityManager.getInstance().getCityByBuilding(pPos);
                if (city != null) {
                    if (pPlayer.isCrouching()) {
                        boolean success = city.getBuildingManager().getBuildingByPos(pPos).increaseLevel(serverLevel, Inhabitant.fromPlayer(pPlayer));
                        return InteractionResult.sidedSuccess(pLevel.isClientSide);
                    }
                    pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos), new FriendlyByteBufHelper(city.getId())::writeUUID);
                }
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        return new SimpleMenuProvider(
                (pContainerId, pPlayerInventory, pPlayer) -> new CityOverviewMenu(pContainerId, pPlayerInventory, CityManager.getInstance().getCityByBuilding(pPos).getId()),
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
        city.getBuildingManager().addBuilding(pos, this);
        return false;
    }

    @Override
    public BuildingEntry getBuildingType() {
        return ModBuildingRegistry.TOWNHALL.get();
    }
}
