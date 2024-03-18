package com.freezzah.minecities.blocks.building.registry;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.building.*;
import com.freezzah.minecities.blocks.registry.ModBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.function.Supplier;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModBuildingRegistry {
    public static final ResourceKey<Registry<BuildingEntry>> BUILDING_ENTRY_REGISTRY_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "buildingentries"));

    public static Registry<BuildingEntry> buildingRegistry =  new RegistryBuilder<>(BUILDING_ENTRY_REGISTRY_KEY)
            .defaultKey(new ResourceLocation(Constants.MOD_ID, "null"))
            .maxId(256).create();

    public final static DeferredRegister<BuildingEntry> DEFERRED_REGISTER =
            DeferredRegister.create(ModBuildingRegistry.buildingRegistry, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    public static final Supplier<BuildingEntry> bank = DEFERRED_REGISTER.register(ModBuildingId.BANK_ID, () -> new BuildingEntry.Builder()
                .setBuildingBlock(ModBlock.BANK_BLOCK.get())
                .setBuildingProducer(BankBuilding::new)
                .setRegistryName(new ResourceLocation(MOD_ID, ModBuildingId.BANK_ID))
                .createBuildingEntry());
    public static final Supplier<BuildingEntry> townhall = DEFERRED_REGISTER.register(ModBuildingId.TOWNHALL_ID, () -> new BuildingEntry.Builder()
                .setBuildingBlock(ModBlock.TOWNHALL_BLOCK.get())
                .setBuildingProducer(TownhallBuilding::new)
                .setRegistryName(new ResourceLocation(MOD_ID, ModBuildingId.TOWNHALL_ID))
                .createBuildingEntry());
    public static final Supplier<BuildingEntry> house = DEFERRED_REGISTER.register(ModBuildingId.HOUSE_ID, () -> new BuildingEntry.Builder()
            .setBuildingBlock(ModBlock.HOUSE_BLOCK.get())
            .setBuildingProducer(HouseBuilding::new)
            .setRegistryName(new ResourceLocation(MOD_ID, ModBuildingId.HOUSE_ID))
            .createBuildingEntry());


    public static final Supplier<BlockEntityType<? extends TileEntityBuilding>> building = BLOCK_ENTITIES.register(
            "blockentitytypes", () ->
            BlockEntityType.Builder.of(TileEntityBuilding::new, ModBlock.getBuildingSuppliers()).build(null));

    public static void register(IEventBus modEventBus) {
        DEFERRED_REGISTER.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
    }
}
