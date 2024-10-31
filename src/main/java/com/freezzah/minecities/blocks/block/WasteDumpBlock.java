package com.freezzah.minecities.blocks.block;

import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.client.gui.menu.WasteMenu;
import com.freezzah.minecities.utils.FriendlyByteBufHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WasteDumpBlock extends AbstractBuildingBlock {

    public WasteDumpBlock(@NotNull Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull BuildingEntry getBuildingType() {
        return ModBuildingRegistry.WASTE_DUMP.get();
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (!level.isClientSide) {
            City city = CityManager.getInstance().getCityByBuilding(pos);
            if (city != null) {
                MenuProvider menuProvider = state.getMenuProvider(level, pos);
                if (menuProvider == null) {
                    return InteractionResult.FAIL;
                }
                player.openMenu(menuProvider, new FriendlyByteBufHelper(city.getId(), pos)::writeUUIDAndBlockPos);
            }
        }
        return InteractionResult.SUCCESS_SERVER; //MIGRATION MAYBE WRONG
    }


    @Override
    public @Nullable MenuProvider getMenuProvider(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
        City city = CityManager.getInstance().getCityByBuilding(pPos);
        if (city == null) {
            return null;
        }
        friendlyByteBuf.writeUUID(city.getId()).writeBlockPos(pPos);
        return new SimpleMenuProvider(
                (pContainerId, pPlayerInventory, pPlayer) -> new WasteMenu(pContainerId, pPlayerInventory, friendlyByteBuf),
                Component.literal("mymenu"));
    }
}
