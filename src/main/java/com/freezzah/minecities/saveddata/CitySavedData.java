package com.freezzah.minecities.saveddata;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.utils.NBTHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CitySavedData extends SavedData {
    private static final String TAG_CITIES = "cities";
    private final List<City> cities = new ArrayList<>();

    public CitySavedData() {
        this.setDirty();
    }

    public static @NotNull CitySavedData load(@NotNull CompoundTag compoundTag) {
        CitySavedData savedData = new CitySavedData();
        for (final Tag tag : compoundTag.getList(TAG_CITIES, Tag.TAG_COMPOUND)) {
            final City city = City.load((CompoundTag) tag);
            if (city != null) {
                savedData.cities.add(city);
            }
        }
        return savedData;
    }

    public @NotNull CompoundTag save(@NotNull CompoundTag compoundTag) {
        ListTag tag = cities.stream().map(City::getCityTag).filter(Objects::nonNull).collect(NBTHelper.toListNBT());
        compoundTag.put(TAG_CITIES, tag);
        return compoundTag;
    }

    public void add(City city) {
        // In some method within the class
        cities.add(city);
        setDirty();
    }

    public @Nullable City getById(@NotNull UUID uuid){
        return cities.stream().filter(c -> c.getId().equals(uuid)).findFirst().orElse(null);
    }

    public @Nullable City getCityByPlayer(@NotNull IInhabitant inhabitant) {
        for(City city : cities) {
            if(city.getPlayers().contains(inhabitant)) return city;
        }
        return null;
    }

    public List<City> getCities() {
        return cities;
    }
}
