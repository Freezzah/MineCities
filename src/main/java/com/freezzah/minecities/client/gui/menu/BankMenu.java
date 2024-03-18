package com.freezzah.minecities.client.gui.menu;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.tag.CityTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BankMenu extends AbstractContainerMenu {

    private City city;

    //Client
    public BankMenu(int containerId, Inventory inventory, @NotNull FriendlyByteBuf buf) {
        this(containerId, inventory, City.load(buf.readNbt().getCompound(CityTags.TAG_CITY)));
    }

    //Server
    public BankMenu(int containerId, Inventory _unused, City city){
        super(ModMenuType.BANK_MENU.get(), containerId);
        this.city = city;
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
        return city.getEconomyManager().getGold();
    }
}
