package com.freezzah.minecities.items;

import com.freezzah.minecities.blocks.block.registry.ModBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
    public static final Supplier<BlockItem> TOWNHALL_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(ModBlock.TOWNHALL_BLOCK);
    public static final Supplier<BlockItem> BANK_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(ModBlock.BANK_BLOCK);
    public static final Supplier<BlockItem> HOUSE_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(ModBlock.HOUSE_BLOCK);
    public static final Supplier<BlockItem> WATER_COLLECTOR_BLOCK_ITEM
            =ITEMS.registerSimpleBlockItem(ModBlock.WATER_COLLECTOR_BLOCK);
    public static final Supplier<BlockItem> FARM_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(ModBlock.FARM_BLOCK);
    public static final Supplier<BlockItem> WELL_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(ModBlock.WELL_BLOCK);
    public static final Supplier<BlockItem> WASTE_DUMP_BLOCK_ITEM =
            ITEMS.registerSimpleBlockItem(ModBlock.WASTE_DUMP_BLOCK);
    public static final Supplier<Item> COIN =
            ITEMS.register(ModItemId.COIN_ITEM_ID, () -> new Item(new Item.Properties().setId(
                    ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, ModItemId.COIN_ITEM_ID))).stacksTo(256)));
    /*
     * Function to call from mod init phase to register all items
     */
    public static void register(@NotNull IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
