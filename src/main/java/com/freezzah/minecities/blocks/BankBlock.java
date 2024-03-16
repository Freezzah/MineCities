package com.freezzah.minecities.blocks;

import com.freezzah.minecities.blocks.building.*;
import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuilding;

public class BankBlock extends AbstractMineCitiesBlock implements IBuildingBlock {

    public BankBlock(Properties properties) {
        super(properties);
    }

    public BuildingEntry getBuildingType(){
        return ModBuilding.bank.get();
    }
}
