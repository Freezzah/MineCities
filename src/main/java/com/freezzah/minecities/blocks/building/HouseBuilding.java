package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.extensions.ITaxable;
import com.freezzah.minecities.city.extensions.IWasteGenerator;
import com.freezzah.minecities.tag.CityTags;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class HouseBuilding extends AbstractBuilding implements  ITaxable,
                                                                IWasteGenerator {

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

    @Override
    public long generateWaste() {
        return (long) this.villagers * getWasteFactor();
    }

    private int getWasteFactor() {
        return 1;
    }

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag superTag = super.write();
        superTag.putInt(CityTags.TAG_VILLAGER_COUNT, villagers);
        return superTag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {
        super.read(tag);
        this.villagers = tag.getInt(CityTags.TAG_VILLAGER_COUNT);
    }
}
