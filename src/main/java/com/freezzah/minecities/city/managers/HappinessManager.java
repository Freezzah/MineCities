package com.freezzah.minecities.city.managers;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.extensions.ILiveable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HappinessManager extends AbstractCityManager {
    public HappinessManager(City city) {
        super(city);
    }

    public static @Nullable HappinessManager load(@NotNull CompoundTag tag, @NotNull City city) {
        try {
            HappinessManager happinessManager = new HappinessManager(city);
            happinessManager.read(tag);
            return happinessManager;
        } catch (Exception e) {
            Constants.LOGGER.warn("Something went wrong loading the cities");
        }
        return null;
    }

    public void tick(@NotNull Level level) {
    }

    public void tickSlow(@NotNull Level level) {
        List<ILiveable> buildingList = getCity().getBuildingManager().getBuildingWithManager(ILiveable.class);
        float happiness = 0;
        int count = 0;
        for (ILiveable building : buildingList) {
            float buildingHappiness = building.calculateHappiness();
            if (buildingHappiness < 0.8f) { //TODO move this value
                building.tickUnhappy();
                if (building.getTicksUnhappy() > 10) { //todo move value
                    building.removeUnhappyInhabitant();
                }
            } else if (buildingHappiness == 1) {
                building.tickHappy();
                if (building.getTicksHappy() > 10) {
                    building.addHappyVillager();
                }
            }
            if (count == 0)
                happiness = building.calculateHappiness();
            else
                happiness += building.calculateHappiness();
            count++;
        }
        if (count == 0)
            return;
        //TODO this code is defo wrong
        //noinspection UnusedAssignment
        happiness = happiness / count;
    }

    public int getTotalInhabitants() {
        List<ILiveable> buildingList = getCity().getBuildingManager().getBuildingWithManager(ILiveable.class);
        return buildingList.stream().mapToInt(ILiveable::getVillagers).sum();
    }

    @Override
    public @NotNull CompoundTag write() {
        return new CompoundTag();
    }

    @Override
    public void read(@NotNull CompoundTag tag) {

    }
}
