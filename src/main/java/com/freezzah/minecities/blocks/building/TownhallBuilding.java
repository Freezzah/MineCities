package com.freezzah.minecities.blocks.building;

import java.util.List;

public class TownhallBuilding extends AbstractBuilding {
    @Override
    public List<Integer> getGoldUpgradeRequirements() {
        return List.of(0, 200);
    }

    @Override
    public boolean checkLevelRequirements(byte desiredLevel) {
        return true;
    }
}
