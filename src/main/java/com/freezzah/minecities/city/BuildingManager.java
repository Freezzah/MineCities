package com.freezzah.minecities.city;

import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.TownhallBuilding;
import com.freezzah.minecities.utils.BlockPosHelper;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BuildingManager {

    private final List<IBuilding> buildings = new ArrayList<>();
    private final City city;

    public BuildingManager(@NotNull City city) {
        this.city = city;
    }

    public boolean addBuilding(@NotNull IBuilding building) {
        return buildings.add(building);
    }

    public boolean removeBuilding(@NotNull IBuilding building) {
        return buildings.remove(building);
    }

    public @NotNull TownhallBuilding getTownhall(){
        for (IBuilding building: buildings) {
            if(building instanceof TownhallBuilding)
                return (TownhallBuilding) building;
        }
        return null;
    }

    public @Nullable IBuilding getBuildingFromPos(@NotNull BlockPos pos) {
        return buildings.stream().filter(b -> BlockPosHelper.equals(pos, b.getBlockPos())).findFirst().orElse(null);
    }
}
