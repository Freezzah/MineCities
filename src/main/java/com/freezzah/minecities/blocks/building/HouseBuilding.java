package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.extensions.*;
import com.freezzah.minecities.tag.CityTags;
import com.freezzah.minecities.utils.Requirement;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HouseBuilding extends AbstractBuilding implements ILiveable,
                                                               IWasteGenerator,
                                                               IFoodConsumer,
                                                               IWaterConsumer {
    //region Properties
    private static final int VILLAGER_TAX = 1;
    private int villagers = 0;
    private float waterSatisfiedFactor = 1;
    private float foodSatisfiedFactor = 1;
    private float happiness = 1;
    private int ticksUnhappy = 0;
    private int ticksHappy = 0;
    //endregion

    //region Constructor
    public HouseBuilding() {
    }
    //endregion

    //region ILiveable
    @Override
    public int getVillagers(){
        return villagers;
    }
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
        return Math.max(0, Math.min(1, happiness + foodSatisfiedFactor + waterSatisfiedFactor));
    }
    @Override
    public int collectTax() {
        return villagers * VILLAGER_TAX;
    }

    @Override
    public float getHappiness() {
        return happiness;
    }

    @Override
    public void tickUnhappy() {
        if(getVillagers() != 0) {
            ticksUnhappy++;
        }
        ticksHappy = 0;
    }

    @Override
    public int getTicksUnhappy(){
        return ticksUnhappy;
    }

    @Override
    public void removeUnhappyInhabitant(){
        this.ticksUnhappy = 0;
        removeVillager();
    }

    @Override
    public void tickHappy() {
        if(getVillagers() < getMaxVillagers()) {
            ticksHappy++;
        }
        ticksUnhappy = 0;
    }

    @Override
    public int getTicksHappy(){
        return ticksHappy;
    }

    @Override
    public void addHappyVillager() {
        ticksHappy = 0;
        addVillager();
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
        //Empty house, skip
        if(villagers == 0) {
            //Do update happiness, otherwise nobody will join back
            if(food > 0) {
                foodSatisfiedFactor = getFoodSatisfiedRatio();
            }
            return 0;
        }
        int foodConsumptionRequired = villagers * getFoodConsumptionPerInhabitant();
        double consumed = Math.min(foodConsumptionRequired, food);
        if (consumed < foodConsumptionRequired)
            foodSatisfiedFactor = -getFoodSatisfiedRatio();
        else
            foodSatisfiedFactor = getFoodSatisfiedRatio();
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
        //Empty house, skip
        if(villagers == 0) {
            return 0;
        }
        int waterConsumptionRequired = villagers * getWaterConsumptionPerInhabitant();
        long consumed = Math.min(waterConsumptionRequired, water);
        //Prevent division by 0
        if(consumed < waterConsumptionRequired)
            waterSatisfiedFactor = -getWaterSatisfiedRatio();
        else
            waterSatisfiedFactor = getWaterSatisfiedRatio();
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
    @NotNull
    List<Requirement> getRequirements() {
        return List.of(new Requirement(0, 0, 0));
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
        superTag.putInt(CityTags.TAG_TICKS_UNHAPPY, ticksUnhappy);
        superTag.putInt(CityTags.TAG_TICKS_HAPPY, ticksHappy);
        return superTag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {
        super.read(tag);
        this.villagers = tag.getInt(CityTags.TAG_VILLAGER_COUNT);
        this.foodSatisfiedFactor = tag.getFloat(CityTags.TAG_FOOD_HAPPINESS);
        this.waterSatisfiedFactor = tag.getFloat(CityTags.TAG_WATER_HAPPINESS);
        this.happiness = tag.getFloat(CityTags.TAG_HAPPINESS);
        this.ticksUnhappy = tag.getInt(CityTags.TAG_TICKS_UNHAPPY);
        this.ticksHappy = tag.getInt(CityTags.TAG_TICKS_HAPPY);
    }
    //endregion
}
