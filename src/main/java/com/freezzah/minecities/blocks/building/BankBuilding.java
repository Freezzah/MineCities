package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.utils.Requirement;

import java.util.Arrays;
import java.util.List;

public class BankBuilding extends AbstractBuilding {
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
