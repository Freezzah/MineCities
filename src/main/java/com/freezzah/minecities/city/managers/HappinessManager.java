package com.freezzah.minecities.city.managers;

import com.freezzah.minecities.city.City;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HappinessManager extends AbstractCityManager{
    public HappinessManager(City city) {
        super(city);
    }

    public void tick(Level level){}
    public void tickSlow(Level level){}

    @Override
    public @NotNull CompoundTag write() {
        return new CompoundTag();
    }

    @Override
    public void read(@NotNull CompoundTag tag) {

    }
}
