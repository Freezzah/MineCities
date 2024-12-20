package com.freezzah.minecities.city;

import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.saveddata.CitySavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CityManager implements ICityManager {
    static CityManager instance;
    private final CitySavedData savedData;

    public CityManager(@NotNull ServerLevel level) {
        savedData = level.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(CitySavedData::new, CitySavedData::load), "minecities_cities");
        //TODO, this seems to break if other worlds load later than the overworld
        if (level.dimension() == Level.OVERWORLD)
            instance = this;
    }

    public static @NotNull CityManager getInstance() {
        return instance;
    }

    @Override
    public @Nullable City createCity(@NotNull IInhabitant inhabitant) {
        if (getCityByPlayer(inhabitant) != null) {
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

    @Override
    public @Nullable City getCityByPlayer(@NotNull IInhabitant inhabitant) {
        return savedData.getCityByPlayer(inhabitant);
    }

    @Override
    public boolean addInhabitant(@NotNull City city, @NotNull IInhabitant inhabitant) {
        if (savedData.getCityByPlayer(inhabitant) != null)
            return false;
        city.addInhabitant(inhabitant);
        return true;
    }

    @Override
    public void setOwner(@NotNull City city, @NotNull IInhabitant inhabitant) {
        city.setOwner(inhabitant);
    }

    @Override
    public void destroyCity(@NotNull City city) {
        savedData.destroyCity(city);
    }

    @Override
    public void removeUnassociatedBuilding(BlockPos pos) {
        savedData.removeUnassociatedBuilding(pos);
    }

    @Override
    public @Nullable City getCityByBuilding(BlockPos pos) {
        return savedData.getCityByBuilding(pos);
    }

    @Override
    public void save() {
        this.savedData.setDirty(true);
    }

    @Override
    public void tickSlow(Level level) {
        for (City city : savedData.getCities()) {
            city.tickSlow(level);
        }
    }

    @Override
    public void tick(Level level) {
        for (City city : savedData.getCities()) {
            city.tick(level);
        }
    }

    @Override
    public void markDirty() {
        savedData.setDirty();
    }
}
