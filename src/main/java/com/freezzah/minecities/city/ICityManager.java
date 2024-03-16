package com.freezzah.minecities.city;

import com.freezzah.minecities.blocks.AbstractMineCitiesBlock;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.entities.IInhabitant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface ICityManager {
    @Nullable City createCity(IInhabitant owner);

    @Nullable City getCityById(@NotNull UUID id);

    boolean addInhabitant(@NotNull City city, @NotNull IInhabitant inhabitant);

    void setOwner(@NotNull City city, @NotNull IInhabitant inhabitant);

    void addBuilding(@NotNull City city, @NotNull IBuilding building);

    void destroyCity(@NotNull City city);

    void removeBuilding(@NotNull City city, @NotNull AbstractMineCitiesBlock abstractMineCitiesBlock);

    void tick(Level level);
}
