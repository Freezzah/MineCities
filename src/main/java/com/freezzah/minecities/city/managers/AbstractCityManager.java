package com.freezzah.minecities.city.managers;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.city.City;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCityManager implements IManager {
    private City city;
    private boolean isDirty = false;
    private CompoundTag managerTag;

    public AbstractCityManager(City city) {
        this.city = city;
        setDirty(true);
    }

    @Override
    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
        city.setDirty(dirty);
    }

    @Override
    public void checkDirty() {
        if (isDirty) {
            this.refresh();
        }
    }

    public void tickSlow(Level level) {
    }
    public void tick(Level level) {
        checkDirty();
    }

    private void refresh() {
        this.write();
    }

    @Override
    public @NotNull CompoundTag getManagerTag() {
        try {
            if (this.managerTag == null || this.isDirty) {
                this.write();
            }
        } catch (final Exception e) {
            Constants.LOGGER.warn("Something went wrong persisting building manager of city: " + city.getId(), e);
        }
        return this.managerTag;
    }

    public void setManagerTag(CompoundTag managerTag) {
        this.managerTag = managerTag;
    }

    public City getCity() {
        return city;
    }
}
