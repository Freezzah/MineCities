package com.freezzah.minecities.items;

import com.freezzah.minecities.blocks.ModBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

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
    public static final Supplier<Item> TOWNHALL_BLOCK_ITEM =
            ITEMS.register(ModItemId.TOWNHALL_BLOCK_ITEM_ID, () -> new ItemNameBlockItem(ModBlock.TOWNHALL_BLOCK.get(), new Item.Properties()));
    public static final Supplier<Item> BANK_BLOCK_ITEM =
            ITEMS.register(ModItemId.BANK_BLOCK_ITEM_ID, () -> new ItemNameBlockItem(ModBlock.BANK_BLOCK.get(), new Item.Properties()));
    /*
     * Function to call from mod init phase to register all items
     */
    public static void register(@NotNull IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
