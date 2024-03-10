package com.freezzah.minecities.city;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.entities.Inhabitant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.*;

public class City {
    private static final String TAG_CITY = "city";
    private static final String TAG_CITY_ID = "cityid";
    private final List<Inhabitant> inhabitants = new ArrayList<>();
    private final Map<Inhabitant, LocalDateTime> joinDate = new HashMap<>();
    private String name;
    private Inhabitant creator;
    private BuildingManager buildingManager;
    private CompoundTag cityTag;
    private boolean isDirty = true;
    private UUID id;

    public City(UUID id) {
        this.id = id;
    }

    /*
     * NBT related methods
     */

    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
    }

    private void checkDirty() {
        if (isDirty) {
            this.refresh();
        }
    }

    private void refresh() {
        this.write(new CompoundTag());
    }

    public CompoundTag getCityTag() {
        try {
            if (this.cityTag == null || this.isDirty) {
                this.write(new CompoundTag());
            }
        } catch (final Exception e) {
            Constants.LOGGER.warn("Something went wrong persisting colony: " + id, e);
        }
        return this.cityTag;
    }

    public @NotNull CompoundTag write(@NotNull CompoundTag tag) {
        tag.putUUID(TAG_CITY_ID, this.id);
        this.cityTag = tag;
        return tag;
    }
    public void read(@NotNull CompoundTag tag) {
        this.id = tag.getUUID(TAG_CITY_ID);
    }

    public static @Nullable City load(@NotNull CompoundTag tag) {
        try {
            UUID id = tag.getUUID(TAG_CITY_ID);
            City city = new City(id);
            city.read(tag);
            return city;
        } catch (Exception e) {
            Constants.LOGGER.warn("Something went wrong loading the municipalities");
        }
        return null;
    }

    public @NotNull FriendlyByteBuf putInFriendlyByteBuf(@NotNull FriendlyByteBuf friendlyByteBuf) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put(TAG_CITY, getCityTag());
        return friendlyByteBuf.writeNbt(compoundTag);
    }
}
