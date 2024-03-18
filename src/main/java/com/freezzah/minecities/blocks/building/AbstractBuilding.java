package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.tag.BuildingTags;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;


public abstract class AbstractBuilding implements IBuilding {
    private BuildingEntry buildingType = null;

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.putString(BuildingTags.TAG_BUILDING_TYPE, this.getBuildingType().getRegistryName().toString());
        return tag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {

    }

    @Override
    public final BuildingEntry getBuildingType() {
        return this.buildingType;
    }

    @Override
    public void setBuildingType(final BuildingEntry buildingType) {
        this.buildingType = buildingType;
    }
}
