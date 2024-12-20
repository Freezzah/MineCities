package com.freezzah.minecities.blocks.block;

import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.client.gui.menu.BankMenu;
import com.freezzah.minecities.items.ModItem;
import com.freezzah.minecities.utils.FriendlyByteBufHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BankBlock extends AbstractBuildingBlock {
    public BankBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BuildingEntry getBuildingType() {
        return ModBuildingRegistry.BANK.get();
    }

    @Override
    public @NotNull InteractionResult useItemOn(@NotNull ItemStack itemStack, @NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            City city = CityManager.getInstance().getCityByBuilding(pPos);
            if (city != null) {
                if (itemStack.getItem() == ModItem.COIN.get()) {
                    ItemStack stack = pPlayer.getMainHandItem();
                    int count = stack.getCount();
                    stack.setCount(0);
                    city.getEconomyManager().addGold(count);
                } else if (pPlayer.isCrouching() && pPlayer.getMainHandItem().isEmpty()) {
                    int count = (int) city.getEconomyManager().withdrawStack();
                    pPlayer.addItem(new ItemStack(ModItem.COIN.get(), count));
                } else {
                    MenuProvider menuProvider = pState.getMenuProvider(pLevel, pPos);
                    if (menuProvider == null) {
                        return InteractionResult.FAIL;
                    }
                    pPlayer.openMenu(menuProvider, new FriendlyByteBufHelper(city.getId())::writeUUID);
                }
            }
        }
        return InteractionResult.SUCCESS_SERVER; //MIGRATION MAYBE WRONG
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        City city = CityManager.getInstance().getCityByBuilding(pPos);
        if (city == null) {
            return null;
        }
        return new SimpleMenuProvider(
                (pContainerId, pPlayerInventory, pPlayer) -> new BankMenu(pContainerId, pPlayerInventory, city.getId()),
                Component.literal("mymenu"));
    }
}
