package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.City;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public enum EnumBuilding {
    TOWNHALL(TownhallBuilding::new, EnumIds.TOWNHALL_BYTE);

    private final BiFunction<City, BlockPos, IBuilding> function;
    private final byte id;

    EnumBuilding(@NotNull BiFunction<City, BlockPos, IBuilding> function, byte id) {
        this.function = function;
        this.id = id;
    }

    public static @Nullable IBuilding fromByteType(byte type, @NotNull City city, @NotNull BlockPos pos) {
        for (EnumBuilding b : EnumBuilding.values()) {
            if (b.id == type) {
                return b.function.apply(city, pos);
            }
        }
        return null;
    }

    public static class EnumIds {
        public static final byte TOWNHALL_BYTE = (byte) 1;
    }
}
