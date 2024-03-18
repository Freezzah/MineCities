package com.freezzah.minecities.city.managers;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public interface IManager {
    void setDirty(boolean dirty);

    void checkDirty();

    @NotNull CompoundTag getManagerTag();

    @NotNull CompoundTag write();

    void read(@NotNull CompoundTag tag);
}
