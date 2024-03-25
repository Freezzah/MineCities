package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.utils.ITaggable;
import net.minecraft.server.level.ServerLevel;

public interface IBuilding extends ITaggable {
    BuildingEntry getBuildingType();
    void setBuildingType(BuildingEntry buildingType);

    byte getBuildingLevel();

    byte getMaxLevel();

    boolean increaseLevel(ServerLevel level, IInhabitant initiator);
}
