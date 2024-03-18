package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.ITaxable;

public class HouseBuilding extends AbstractBuilding implements ITaxable {

    private static final int VILLAGER_TAX = 1;
    private int villagers = 0;
    public HouseBuilding() {
    }

    public void addVillager() {
        villagers +=1;
    }
    
    public void removeVillager(){
        villagers = Math.max(0, villagers - 1);
    }
    
    @Override
    public int collectTax() {
        return villagers * VILLAGER_TAX;
    }
}
