package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.utils.Requirement;

import java.util.Arrays;
import java.util.List;

public class TownhallBuilding extends AbstractBuilding {

    @Override
    List<Requirement> getRequirements() {
        return Arrays.asList(new Requirement(0,0,0));
    }

    @Override
    public boolean checkLevelRequirements(byte desiredLevel) {
        return true;
    }
}
