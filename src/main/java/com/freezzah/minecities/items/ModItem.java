package com.freezzah.minecities.items;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModItem {

    /*
     * Registry for Items.
     */
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MOD_ID);

    /*
     * All items added by this mod.
     */

    /*
     * Function to call from mod init phase to register all items
     */
    public static void register(@NotNull IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
