package com.freezzah.minecities.blocks.building.registry;

import com.freezzah.minecities.Constants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModBuildingRegistry {
    public static final ResourceKey<Registry<BuildingEntry>> BUILDING_ENTRY_REGISTRY_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation(MOD_ID, "buildingentries"));
    public static final RegistryBuilder<BuildingEntry> buildingRegistryBuilder =
            new RegistryBuilder<>(BUILDING_ENTRY_REGISTRY_KEY)
                    .defaultKey(new ResourceLocation(Constants.MOD_ID, "null"))
                    .maxId(256);
    public static Registry<BuildingEntry> buildingRegistry;
}
