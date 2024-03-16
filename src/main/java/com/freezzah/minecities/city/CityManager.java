package com.freezzah.minecities.city;

import com.freezzah.minecities.blocks.AbstractMineCitiesBlock;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.IBuildingBlock;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.saveddata.CitySavedData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public @Nullable City createCity(@NotNull IInhabitant inhabitant){
        if(getCityByPlayer(inhabitant) != null) {
            return null;
        }
        City city = new City(UUID.randomUUID());
        setOwner(city, inhabitant);
        addInhabitant(city, inhabitant);
        this.savedData.add(city);
        this.savedData.setDirty();
        return city;
    }

    @Override
    public @Nullable City getCityById(@NotNull UUID id) {
        return savedData.getById(id);
    }

    public static @NotNull CityManager getInstance(){
        return instance;
    }

    public @Nullable City getCityByPlayer(@NotNull IInhabitant inhabitant) {
        return savedData.getCityByPlayer(inhabitant);
    }

    @Override
    public boolean addInhabitant(@NotNull City city, @NotNull IInhabitant inhabitant) {
        if(savedData.getCityByPlayer(inhabitant) != null)
            return false;
        city.addInhabitant(inhabitant);
        return true;
    }

    @Override
    public void setOwner(@NotNull City city, @NotNull IInhabitant inhabitant) {
        city.setOwner(inhabitant);
    }

    public void addBuilding(@NotNull City city, @NotNull IBuildingBlock building) {
        city.addBuilding(city.getBuildingManager().createFrom(city, building));
    }
    @Override
    public void addBuilding(@NotNull City city, @NotNull IBuilding building) {
        city.addBuilding(building);
    }

    @Override
    public void destroyCity(@NotNull City city) {

    }

    @Override
    public void removeBuilding(@NotNull City city, @NotNull AbstractMineCitiesBlock abstractMineCitiesBlock) {

    }

    @Override
    public void tick(Level level) {
        for(City city : savedData.getCities()){
            city.tick(level);
        }
    }
}
