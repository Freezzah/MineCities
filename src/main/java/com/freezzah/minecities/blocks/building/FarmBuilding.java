package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.extensions.IFoodGenerator;
import com.freezzah.minecities.utils.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FarmBuilding extends AbstractBuilding implements IFoodGenerator {

    @Override
    List<Requirement> getRequirements() {
        return Arrays.asList(new Requirement(100, 100, 100));
    }

    @Override
    public boolean checkLevelRequirements(byte desiredLevel) {
        return true;
    }

    @Override
    public double generateFood() {
        return 10;
    }
}
