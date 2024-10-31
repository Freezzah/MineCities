package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.extensions.IWaterGeneratorNearby;
import com.freezzah.minecities.utils.Requirement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WellBuilding extends AbstractBuilding implements IWaterGeneratorNearby {
    //region IBuilding
    @Override
    @NotNull
    List<Requirement> getRequirements() {
        return List.of(new Requirement(0, 0, 0));
    }

    @Override
    public boolean checkLevelRequirements(byte desiredLevel) {
        return true;
    }
    //endregion
}
