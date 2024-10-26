package com.freezzah.minecities.items;

import com.freezzah.minecities.blocks.block.registry.ModBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
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
    public static final Supplier<Item> HOUSE_BLOCK_ITEM =
            ITEMS.register(ModItemId.HOUSE_BLOCK_ITEM_ID, () -> new ItemNameBlockItem(ModBlock.HOUSE_BLOCK.get(), new Item.Properties()));
    public static final Supplier<Item> WATER_COLLECTOR_BLOCK_ITEM =
            ITEMS.register(ModItemId.WATER_COLLECTOR_BLOCK_ITEM_ID, () -> new ItemNameBlockItem(ModBlock.WATER_COLLECTOR_BLOCK.get(), new Item.Properties()));
    public static final Supplier<Item> FARM_BLOCK_ITEM =
            ITEMS.register(ModItemId.FARM_BLOCK_ITEM_ID, () -> new ItemNameBlockItem(ModBlock.FARM_BLOCK.get(), new Item.Properties()));
    public static final Supplier<Item> WELL_BLOCK_ITEM =
            ITEMS.register(ModItemId.WELL_BLOCK_ITEM_ID, () -> new ItemNameBlockItem(ModBlock.WELL_BLOCK.get(), new Item.Properties()));
    public static final Supplier<Item> COIN =
            ITEMS.register(ModItemId.COIN_ITEM_ID, () -> new Item(new Item.Properties().stacksTo(256)));

    /*
     * Function to call from mod init phase to register all items
     */
    public static void register(@NotNull IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
