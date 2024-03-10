package com.freezzah.minecities.client.gui.menu;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModMenuType {
    /*
     * Registry for Menu types.
     */
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, MOD_ID);

    /*
     * All items added by this mod.
     */

    /*
     * Function to call from mod init phase to register all blocks
     */
    public static void register(@NotNull IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
