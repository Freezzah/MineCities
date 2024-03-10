package com.freezzah.minecities.city;

import com.freezzah.minecities.saveddata.CitySavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CityManager implements ICityManager {
    static CityManager instance;
    private final ServerLevel level;
    private final CitySavedData savedData;

    public CityManager(@NotNull ServerLevel level) {
        this.level = level;
        savedData = level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(CitySavedData::new, CitySavedData::load), "minecities_cities");
        instance = this;
    }

    @Override
    public void createCity(UUID id){
        City city = new City(id);
        this.savedData.add(city);
        this.savedData.setDirty();
    }

    public static CityManager getInstance(){
        return instance;
    }
}
