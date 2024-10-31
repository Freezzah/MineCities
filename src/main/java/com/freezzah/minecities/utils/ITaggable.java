package com.freezzah.minecities.utils;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public interface ITaggable {
    /**
     * Method that writes its implementation to an NBT Tag.
     *
     * @return tag with the implementation added as a new compound tag
     */
    @NotNull CompoundTag write();

    /**
     * Method that constructs an implementation from an NBT Tag
     *
     * @param tag Tag that contains implementation information
     */
    void read(@NotNull CompoundTag tag);
}

