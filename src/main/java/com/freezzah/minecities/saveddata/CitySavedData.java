package com.freezzah.minecities.saveddata;

import com.freezzah.minecities.city.City;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CitySavedData extends SavedData {
    private static final String TAG_CITIES = "cities";
    private final List<City> cities = new ArrayList<>();

    public CitySavedData() {
        this.setDirty();
    }

    @Contract(" -> new")
    static @NotNull Collector<CompoundTag, ?, ListTag> toListNBT() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    final ListTag tagList = new ListTag();
                    tagList.addAll(list);

                    return tagList;
                });
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
        ListTag tag = cities.stream().map(City::getCityTag).filter(Objects::nonNull).collect(toListNBT());
        compoundTag.put(TAG_CITIES, tag);
        return compoundTag;
    }

    public void add(City city) {
        // In some method within the class
        cities.add(city);
        setDirty();
    }
}
