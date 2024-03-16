package com.freezzah.minecities.blocks.building.bank;

import com.freezzah.minecities.blocks.building.AbstractBuilding;
import com.freezzah.minecities.city.City;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class BankBuilding extends AbstractBuilding {

    public BankBuilding(@NotNull City city) {
        super(city);
    }

    @Override
    public @NotNull CompoundTag write() {
        return null;
    }
}
