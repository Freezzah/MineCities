package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.utils.ITaggable;

public interface IBuilding extends ITaggable {
    BuildingEntry getBuildingType();
    void setBuildingType(BuildingEntry buildingType);
}
