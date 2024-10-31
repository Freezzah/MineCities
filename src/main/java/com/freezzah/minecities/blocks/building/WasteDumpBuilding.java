package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.extensions.IWasteConsumer;
import com.freezzah.minecities.utils.Requirement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WasteDumpBuilding extends AbstractBuilding implements IWasteConsumer {
    //region Properties
    long waste = 0;
    //endregion

    //region IBuilding
    @Override
    @NotNull List<Requirement> getRequirements() {
        return List.of(new Requirement(0, 0, 0));
    }

    @Override
    public boolean checkLevelRequirements(byte desiredLevel) {
        return true;
    }
    //endregion

    //region IWasteConsumer
    @Override
    public long consumeWaste(long waste) {
        long consumableWaste = getMaxWaste() - getCurrentWaste();
        long consumedWaste = Math.min(consumableWaste, getConsumableWastePerTick());
        this.waste += consumedWaste;
        return consumedWaste;
    }

    @Override
    public long getConsumableWastePerTick() {
        return 10;
    }

    @Override
    public long getMaxWaste() {
        return 4000;
    }

    @Override
    public long getCurrentWaste() {
        return waste;
    }
    //endregion
}
