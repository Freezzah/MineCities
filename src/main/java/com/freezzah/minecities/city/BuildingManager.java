package com.freezzah.minecities.city;

import com.freezzah.minecities.blocks.building.IBuilding;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class BuildingManager {

    private final Map<BlockPos, IBuilding> buildings = new HashMap<>();
    private final City city;

    public BuildingManager(@NotNull City city) {
        this.city = city;
    }

    public boolean addBuilding(@NotNull IBuilding building) {
        if (buildings.containsKey(building.getBlockPos())) {
            return false;
        } else {
            buildings.put(building.getBlockPos(), building);
            return true;
        }
    }

    public boolean removeBuilding(@NotNull BlockPos pos) {
        IBuilding key = buildings.remove(pos);
        return key != null;
    }
}
