package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.extensions.IWaterGenerator;

import java.util.List;

public class WaterCollectorBuilding extends AbstractBuilding implements IWaterGenerator {

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
    public List<Integer> getGoldUpgradeRequirements() {
        return List.of(100,200,300,400);
    }

    @Override
    public boolean checkLevelRequirements(byte desiredLevel) {
        return true;
    }
}
