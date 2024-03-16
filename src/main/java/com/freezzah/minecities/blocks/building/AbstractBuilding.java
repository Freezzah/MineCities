package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.city.City;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBuilding implements IBuilding {
    private final City city;
    public static final String TAG_BUILDING_TYPE = "buildingtype";
    private BuildingEntry buildingType = null;

    public AbstractBuilding(City city) {
        this.city = city;
    }

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.putString(TAG_BUILDING_TYPE, this.getBuildingType().getRegistryName().toString());
        return tag;
    }

    public void read(City city, @NotNull CompoundTag tag){
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
