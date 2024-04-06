package com.freezzah.minecities.blocks.block.registry;

import com.freezzah.minecities.blocks.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

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
    public static final @NotNull Supplier<TownhallBlock> TOWNHALL_BLOCK =
            BLOCKS.register(ModBlockId.TOWNHALL_BLOCK_ID, () -> new TownhallBlock(BlockBehaviour.Properties.of()));
    public static final @NotNull Supplier<BankBlock> BANK_BLOCK =
            BLOCKS.register(ModBlockId.BANK_BLOCK_ID, () -> new BankBlock(BlockBehaviour.Properties.of()));
    public static final @NotNull Supplier<HouseBlock> HOUSE_BLOCK =
            BLOCKS.register(ModBlockId.HOUSE_BLOCK_ID, () -> new HouseBlock(BlockBehaviour.Properties.of()));
    public static final @NotNull Supplier<WaterCollectorBlock> WATER_COLLECTOR_BLOCK =
            BLOCKS.register(ModBlockId.WATER_COLLECTOR_BLOCK_ID, () -> new WaterCollectorBlock(BlockBehaviour.Properties.of()));
    public static final @NotNull Supplier<FarmBlock> FARM_BLOCK =
            BLOCKS.register(ModBlockId.FARM_BLOCK_ID, () -> new FarmBlock(BlockBehaviour.Properties.of()));
    public static final @NotNull Supplier<WellBlock> WELL_BLOCK =
            BLOCKS.register(ModBlockId.WELL_BLOCK_ID, () -> new WellBlock(BlockBehaviour.Properties.of()));

    @Contract(value = " -> new", pure = true)
    @NotNull
    public static AbstractBuildingBlock[] getBuildingSuppliers() {
        return new AbstractBuildingBlock[]{
                TOWNHALL_BLOCK.get(), BANK_BLOCK.get(), HOUSE_BLOCK.get(),
                FARM_BLOCK.get(), WATER_COLLECTOR_BLOCK.get(), WELL_BLOCK.get()
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
