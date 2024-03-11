package com.freezzah.minecities.blocks;

import com.freezzah.minecities.blocks.building.BankBuilding;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.IBuildingBlock;

public class BankBlock extends AbstractMineCitiesBlock implements IBuildingBlock {
    public BankBlock(Properties properties) {
        super(properties);
    }

    @Override
    public <T extends IBuilding> Class<T> getType(){
        return (Class<T>) BankBuilding.class;
    }
}
