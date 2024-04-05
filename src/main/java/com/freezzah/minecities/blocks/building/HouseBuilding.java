package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.extensions.*;
import com.freezzah.minecities.tag.CityTags;
import com.freezzah.minecities.utils.Requirement;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class HouseBuilding extends AbstractBuilding implements ILiveable,
                                                               ITaxable,
                                                               IWasteGenerator,
                                                               IFoodConsumer,
                                                               IWaterConsumer {
    //region Properties
    private static final int VILLAGER_TAX = 1;
    private int villagers = 0;
    private float waterSatisfiedFactor = 1;
    private float foodSatisfiedFactor = 1;
    private float happiness = 1;
    //endregion

    //region Constructor
    public HouseBuilding() {
    }
    //endregion

    //region ILiveable
    @Override
    public void addVillager() {
        villagers = Math.min(getMaxVillagers(), villagers + 1);
    }

    @Override
    public int getMaxVillagers() {
        return this.getBuildingLevel() * 2;
    }

    @Override
    public void removeVillager() {
        villagers = Math.max(0, villagers - 1);
    }

    @Override
    public float calculateHappiness() {
        return this.happiness = Math.max(0, Math.min(1, happiness + foodSatisfiedFactor + waterSatisfiedFactor));
    }
    //endregion

    //region ITaxable
    @Override
    public int collectTax() {
        return villagers * VILLAGER_TAX;
    }

    //endregion

    //region IWasteGenerator
    @Override
    public long generateWaste() {
        return (long) this.villagers * getWasteFactor();
    }

    @Override
    public int getWasteFactor() {
        return 1;
    }
    //endregion

    //region IFoodConsumer
    @Override
    public double consumeFood(double food) {
        int foodConsumptionRequired = villagers * getFoodConsumptionPerInhabitant();
        double consumed = Math.min(foodConsumptionRequired, food);
        //Prevent division by 0
        if(foodConsumptionRequired <= 0)
            foodSatisfiedFactor = getFoodSatisfiedRatio();
        else
            foodSatisfiedFactor = -getFoodSatisfiedRatio();
        return consumed;
    }

    @Override
    public int getFoodConsumptionPerInhabitant() {
        return 1;
    }
    @Override
    public float getFoodSatisfiedRatio() {
        return 0.2f;
    }
    //endregion

    //region IWaterConsumer
    @Override
    public long consumeWater(long water) {
        int waterConsumptionRequired = villagers * getWaterConsumptionPerInhabitant();
        long consumed = Math.min(waterConsumptionRequired, water);
        //Prevent division by 0
        if(waterConsumptionRequired <= 0 )
            waterSatisfiedFactor = 1;
        else
            waterSatisfiedFactor = (float) (consumed / waterConsumptionRequired);
        return consumed;
    }

    @Override
    public boolean canConsumeNearbyWater() {
        return true;
    }

    @Override
    public int getWaterConsumptionPerInhabitant() {
        return 1;
    }

    @Override
    public float getWaterSatisfiedRatio() {
        return 0.2f;
    }
    //endregion

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

    //region NBT
    @Override
    public @NotNull CompoundTag write() {
        CompoundTag superTag = super.write();
        superTag.putInt(CityTags.TAG_VILLAGER_COUNT, villagers);
        superTag.putFloat(CityTags.TAG_FOOD_HAPPINESS, foodSatisfiedFactor);
        superTag.putFloat(CityTags.TAG_WATER_HAPPINESS, waterSatisfiedFactor);
        superTag.putFloat(CityTags.TAG_HAPPINESS, happiness);
        return superTag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {
        super.read(tag);
        this.villagers = tag.getInt(CityTags.TAG_VILLAGER_COUNT);
        this.foodSatisfiedFactor = tag.getFloat(CityTags.TAG_FOOD_HAPPINESS);
        this.waterSatisfiedFactor = tag.getFloat(CityTags.TAG_WATER_HAPPINESS);
        this.happiness = tag.getFloat(CityTags.TAG_HAPPINESS);
    }
    //endregion
}
