package com.freezzah.minecities.client.gui.menu;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.city.extensions.IWasteConsumer;
import com.freezzah.minecities.city.managers.BuildingManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class WasteMenu extends AbstractContainerMenu {
    private final City city;
    private final BlockPos pos;

    //Client
    public WasteMenu(int containerId, @NotNull Inventory inventory, @NotNull FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readUUID(), buf.readBlockPos());
    }

    //Server
    public WasteMenu(int containerId, @NotNull Inventory ignored, @NotNull UUID cityUuid, @NotNull BlockPos pos) {
        super(ModMenuType.WASTE_MENU.get(), containerId);
        this.city = CityManager.getInstance().getCityById(cityUuid);
        this.pos = pos;
    }

    //TODO
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        //noinspection DataFlowIssue
        return null;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return true;
    }

    public City getCity() {
        return city;
    }

    public long getWaste() {
        BuildingManager buildingManager = city.getBuildingManager();
        IWasteConsumer wasteConsumer = (IWasteConsumer) buildingManager.getBuildingByPos(this.pos);
        if (wasteConsumer == null) {
            throw new IllegalStateException("Waste consumer not found");
        }
        return wasteConsumer.getCurrentWaste();
    }
}
