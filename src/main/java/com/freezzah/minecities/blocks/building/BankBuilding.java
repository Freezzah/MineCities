package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.city.City;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class BankBuilding extends AbstractBuilding {

    public static final EnumBuilding TYPE = EnumBuilding.BANK;

    public BankBuilding(@NotNull City city, @NotNull BlockPos blockPos) {
        super(city, blockPos);
    }
}
