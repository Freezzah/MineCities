package com.freezzah.minecities.blocks.block.registry;

import com.freezzah.minecities.blocks.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModBlock {
    /*
     * Registry for Blocks.
     */
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(MOD_ID);
    /*
     * All blocks added by this mod.
     */
    public static final @NotNull DeferredHolder<net.minecraft.world.level.block.Block, TownhallBlock> TOWNHALL_BLOCK =
            BLOCKS.register(ModBlockId.TOWNHALL_BLOCK_ID, () -> new TownhallBlock(BlockBehaviour.Properties.of().setId(
                    ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, ModBlockId.TOWNHALL_BLOCK_ID)))));
    public static final @NotNull DeferredHolder<net.minecraft.world.level.block.Block, BankBlock> BANK_BLOCK =
            BLOCKS.register(ModBlockId.BANK_BLOCK_ID, () -> new BankBlock(BlockBehaviour.Properties.of().setId(
                    ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, ModBlockId.BANK_BLOCK_ID)))));
    public static final @NotNull DeferredHolder<net.minecraft.world.level.block.Block, HouseBlock> HOUSE_BLOCK =
            BLOCKS.register(ModBlockId.HOUSE_BLOCK_ID, () -> new HouseBlock(BlockBehaviour.Properties.of().setId(
                    ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, ModBlockId.HOUSE_BLOCK_ID)))));
    public static final @NotNull DeferredHolder<net.minecraft.world.level.block.Block, WaterCollectorBlock> WATER_COLLECTOR_BLOCK =
            BLOCKS.register(ModBlockId.WATER_COLLECTOR_BLOCK_ID, () -> new WaterCollectorBlock(BlockBehaviour.Properties.of().setId(
                    ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, ModBlockId.WATER_COLLECTOR_BLOCK_ID)))));
    public static final @NotNull DeferredHolder<net.minecraft.world.level.block.Block, FarmBlock> FARM_BLOCK =
            BLOCKS.register(ModBlockId.FARM_BLOCK_ID, () -> new FarmBlock(BlockBehaviour.Properties.of().setId(
                    ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, ModBlockId.FARM_BLOCK_ID)))));
    public static final @NotNull DeferredHolder<net.minecraft.world.level.block.Block, WellBlock> WELL_BLOCK =
            BLOCKS.register(ModBlockId.WELL_BLOCK_ID, () -> new WellBlock(BlockBehaviour.Properties.of().setId(
                    ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, ModBlockId.WELL_BLOCK_ID)))));
    public static final @NotNull DeferredHolder<net.minecraft.world.level.block.Block, WasteDumpBlock> WASTE_DUMP_BLOCK =
            BLOCKS.register(ModBlockId.WASTE_DUMP_ID, () -> new WasteDumpBlock(BlockBehaviour.Properties.of().setId(
                    ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(MOD_ID, ModBlockId.WASTE_DUMP_ID)))));

    @Contract(value = " -> new", pure = true)
    @NotNull
    public static AbstractBuildingBlock[] getBuildingSuppliers() {
        return new AbstractBuildingBlock[]{
                TOWNHALL_BLOCK.get(), BANK_BLOCK.get(), HOUSE_BLOCK.get(),
                FARM_BLOCK.get(), WATER_COLLECTOR_BLOCK.get(), WELL_BLOCK.get(), WASTE_DUMP_BLOCK.get()
        };
    }

    /*
     * Function to call from mod init phase to register all blocks
     */
    public static void register(@NotNull IEventBus eventBus) {
        BLOCKS.register(eventBus);
        assert getBuildingSuppliers().length == BLOCKS.getEntries().size();
    }
}
