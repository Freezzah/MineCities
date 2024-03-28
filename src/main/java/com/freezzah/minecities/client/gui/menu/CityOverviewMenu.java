package com.freezzah.minecities.client.gui.menu;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.city.managers.EconomyManager;
import com.freezzah.minecities.city.managers.WasteManager;
import com.freezzah.minecities.city.managers.WaterManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CityOverviewMenu extends AbstractContainerMenu {

    private final City city;

    //Client
    public CityOverviewMenu(int containerId, Inventory inventory, @NotNull FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readUUID());
    }

    //Server
    public CityOverviewMenu(int containerId, Inventory _unused, UUID cityUuid) {
        super(ModMenuType.CITY_OVERVIEW_MENU.get(), containerId);
        this.city = CityManager.getInstance().getCityById(cityUuid);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return true;
    }

    public City getCity() {
        return city;
    }

    public long getMoney() {
        EconomyManager mgr = city.getEconomyManager();
        return mgr.getGold();
    }

    public long getWater() {
        WaterManager mgr = city.getWaterManager();
        return mgr.getWater();
    }

    public long getWaste() {
        WasteManager mgr = city.getWasteManager();
        return mgr.getWaste();
    }
}
