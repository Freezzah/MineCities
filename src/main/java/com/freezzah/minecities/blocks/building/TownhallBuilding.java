package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.City;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class TownhallBuilding extends AbstractBuilding {

    public static final EnumBuilding TYPE = EnumBuilding.TOWNHALL;

    public TownhallBuilding(@NotNull City city, @NotNull BlockPos blockPos) {
        super(city, blockPos);
    }
}
