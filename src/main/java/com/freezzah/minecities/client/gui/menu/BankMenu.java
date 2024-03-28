package com.freezzah.minecities.client.gui.menu;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.city.managers.EconomyManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BankMenu extends AbstractContainerMenu {

    private final City city;

    //Client
    public BankMenu(int containerId, Inventory inventory, @NotNull FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readUUID());
    }

    //Server
    public BankMenu(int containerId, Inventory _unused, UUID cityUuid) {
        super(ModMenuType.BANK_MENU.get(), containerId);
        this.city = CityManager.getInstance().getCityById(cityUuid);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public City getCity() {
        return city;
    }

    public long getMoney() {
        EconomyManager mgr = city.getEconomyManager();
        return mgr.getGold();
    }
}
