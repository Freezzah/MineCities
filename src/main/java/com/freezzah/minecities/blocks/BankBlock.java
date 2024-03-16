package com.freezzah.minecities.blocks;

import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;

public class BankBlock extends AbstractBuildingBlock {

    public BankBlock(Properties properties) {
        super(properties);
    }

    public BuildingEntry getBuildingType(){
        return ModBuildingRegistry.bank.get();
    }
}
