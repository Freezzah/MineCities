package com.freezzah.minecities.saveddata;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.tag.CityTags;
import com.freezzah.minecities.utils.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CitySavedData extends SavedData {
    /**
     * List of cities to track
     */
    private final List<City> cities = new ArrayList<>();
    public CitySavedData() {
        this.setDirty();
    }
    /**
     * Loads SavedData from NBT
     *
     * @param compoundTag tag to load cityData from
     * @return new instance of the cityData
     */
    public static @NotNull CitySavedData load(@NotNull CompoundTag compoundTag) {
        CitySavedData savedData = new CitySavedData();
        for (final Tag tag : compoundTag.getList(CityTags.TAG_CITIES, Tag.TAG_COMPOUND)) {
            final City city = City.load((CompoundTag) tag);
            if (city != null) {
                savedData.cities.add(city);
            }
        }
        return savedData;
    }
    /**
     * Writes SavedData to NBT.
     *
     * @param compoundTag the {@code CompoundTag} to save the {@code SavedData} to
     * @return Compound tag where cities are added.
     */
    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag) {
        ListTag tag = cities.stream().map(City::getCityTag).collect(NBTHelper.toListNBT());
        compoundTag.put(CityTags.TAG_CITIES, tag);
        return compoundTag;
    }
    /**
     * Adds a city to the trackable object
     *
     * @param city {@link City}
     */
    public void add(@NotNull City city) {
        // In some method within the class
        cities.add(city);
        setDirty();
    }
    /**
     * Returns the {@link City} if any exist with UUID, otherwise null
     *
     * @param uuid {@link UUID} of the {@link City}
     * @return {@link City} or null
     */
    public @Nullable City getById(@NotNull UUID uuid) {
        return cities.stream().filter(c -> c.getId().equals(uuid)).findFirst().orElse(null);
    }
    /**
     * Returns the {@link City} if any exist with UUID, otherwise null
     *
     * @param inhabitant {@link IInhabitant} of the {@link City}
     * @return {@link City} or null
     */
    public @Nullable City getCityByPlayer(@NotNull IInhabitant inhabitant) {
        for (City city : cities) {
            if (city.getPlayers().contains(inhabitant)) return city;
        }
        return null;
    }
    public @NotNull List<City> getCities() {
        return cities;
    }
    public void destroyCity(City city) {
        cities.remove(city);
    }
    public void removeUnassociatedBuilding(BlockPos pos) {
        for (City city : cities) {
            if (city.getBuildingManager().getBuildingByPos(pos) != null) {
                city.getBuildingManager().removeBuilding(pos);
            }
        }
    }
    public @Nullable City getCityByBuilding(BlockPos pos) {
        for (City city : cities) {
            if (city.getBuildingManager().getBuildingByPos(pos) != null) {
                return city;
            }
        }
        return null;
    }
}
