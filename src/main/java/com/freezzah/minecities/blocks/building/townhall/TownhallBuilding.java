package com.freezzah.minecities.blocks.building.townhall;

import com.freezzah.minecities.blocks.building.AbstractBuilding;
import com.freezzah.minecities.city.City;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class TownhallBuilding extends AbstractBuilding {

    public TownhallBuilding(@NotNull City city) {
        super(city);
    }

    @Override
    public @NotNull CompoundTag write() {
        super.write();
        return null;
    }
}
