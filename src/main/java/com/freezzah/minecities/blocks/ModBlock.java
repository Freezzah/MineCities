package com.freezzah.minecities.blocks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModBlock {
    /*
     * Registry for Blocks.
     */
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MOD_ID);
    /*
     * All items added by this mod.
     */

    /*
     * Function to call from mod init phase to register all blocks
     */
    public static void register(@NotNull IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
