package com.freezzah.minecities.blocks.building.registry;

import com.freezzah.minecities.blocks.ModBlock;
import com.freezzah.minecities.blocks.building.bank.BankBuilding;
import com.freezzah.minecities.blocks.building.townhall.TownhallBuilding;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.*;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModBuilding {
    public static DeferredHolder<BuildingEntry, BuildingEntry> bank;
    public static DeferredHolder<BuildingEntry, BuildingEntry> townhall;
    public final static DeferredRegister<BuildingEntry> DEFERRED_REGISTER =
            DeferredRegister.create(ModBuildingRegistry.buildingRegistry, MOD_ID);

    static {
        ModBuilding.bank = DEFERRED_REGISTER.register(ModBuildingId.BANK_ID, () -> new BuildingEntry.Builder()
                .setBuildingBlock(ModBlock.BANK_BLOCK.get())
                .setBuildingProducer(BankBuilding::new)
                .setRegistryName(new ResourceLocation(MOD_ID, ModBuildingId.BANK_ID))
                .createBuildingEntry());
        ModBuilding.townhall = DEFERRED_REGISTER.register(ModBuildingId.TOWNHALL_ID, () -> new BuildingEntry.Builder()
                .setBuildingBlock(ModBlock.TOWNHALL_BLOCK.get())
                .setBuildingProducer(TownhallBuilding::new)
                .setRegistryName(new ResourceLocation(MOD_ID, ModBuildingId.TOWNHALL_ID))
                .createBuildingEntry());
    }

    public static void register(){};
}
