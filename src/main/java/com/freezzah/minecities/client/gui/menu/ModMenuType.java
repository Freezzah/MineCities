package com.freezzah.minecities.client.gui.menu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModMenuType {
    /*
     * Registry for Menu types.
     */
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, MOD_ID);

    /*
     * All menus added by this mod.
     */
    public static final Supplier<MenuType<BankMenu>> BANK_MENU = MENUS.register(ModMenuId.BANK_MENU_ID, () ->
            IMenuTypeExtension.create(BankMenu::new));
    public static final Supplier<MenuType<CityOverviewMenu>> CITY_OVERVIEW_MENU = MENUS.register(ModMenuId.CITY_OVERVIEW_MENU_ID, () ->
            IMenuTypeExtension.create(CityOverviewMenu::new));

    /*
     * Function to call from mod init phase to register all blocks
     */
    public static void register(@NotNull IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
