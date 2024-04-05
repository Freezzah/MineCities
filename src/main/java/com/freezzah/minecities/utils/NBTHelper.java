package com.freezzah.minecities.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class NBTHelper {
    /**
     * Returns a collector that collects {@link CompoundTag}s and forms a {@link ListTag}
     * Example usage:
     * {@code myList.stream().map(myObject::getCompoundTag).collect(NBTHelper.toListNBT());}
     * @return Collector
     */
    @Contract(" -> new")
    public static @NotNull Collector<CompoundTag, ?, ListTag> toListNBT() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    final ListTag tagList = new ListTag();
                    tagList.addAll(list);
                    return tagList;
                });
    }
}
