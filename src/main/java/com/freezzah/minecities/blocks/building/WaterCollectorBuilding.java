package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.extensions.IWaterGenerator;
import com.freezzah.minecities.utils.Requirement;

import java.util.Arrays;
import java.util.List;

public class WaterCollectorBuilding extends AbstractBuilding implements IWaterGenerator {
    //region Properties
    int water = 0;
    //endregion

    //region Constructors
    public void collectWater(int waterCollected) {
        water += waterCollected;
    }
    //endregion

    //region IWaterGenerator
    @Override
    public long generateWater() {
        int returnWater = water;
        this.water = 0;
        return returnWater;
    }
    //endregion

    //region IBuilding
    @Override
    List<Requirement> getRequirements() {
        return Arrays.asList(new Requirement(0,0,0));
    }
    @Override
    public boolean checkLevelRequirements(byte desiredLevel) {
        return true;
    }
    //endregion
}
