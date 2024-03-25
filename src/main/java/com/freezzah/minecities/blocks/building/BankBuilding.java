package com.freezzah.minecities.blocks.building;

import java.util.List;
import java.util.Map;

public class BankBuilding extends AbstractBuilding {
    @Override
    public List<Integer> getGoldUpgradeRequirements() {
        return List.of(100,200);
    }

    @Override
    public boolean checkLevelRequirements(byte desiredLevel) {
        return true;
    }
}
