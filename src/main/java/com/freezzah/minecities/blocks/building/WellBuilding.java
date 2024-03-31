package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.extensions.IWaterGenerator;
import com.freezzah.minecities.utils.Requirement;

import java.util.Arrays;
import java.util.List;

public class WellBuilding extends AbstractBuilding implements IWaterGenerator {
    int water = 0;

    public void collectWater(int waterCollected) {
        water += waterCollected;
    }

    @Override
    public long generateWater() {
        int returnWater = water;
        this.water = 0;
        return returnWater;
    }

    @Override
    List<Requirement> getRequirements() {
        return Arrays.asList(new Requirement(0,0,0));
    }

    @Override
    public boolean checkLevelRequirements(byte desiredLevel) {
        return true;
    }
}
