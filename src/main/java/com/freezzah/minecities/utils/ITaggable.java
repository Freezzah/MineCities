package com.freezzah.minecities.utils;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public interface ITaggable {
    @NotNull CompoundTag write();
}

