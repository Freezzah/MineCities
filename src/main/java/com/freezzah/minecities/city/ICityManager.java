package com.freezzah.minecities.city;

import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.IBuildingBlock;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.entities.IInhabitant;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface ICityManager {
    /**
     * Creates a city. Makes {@param owner} the owner of the city.
     * If the {@link com.freezzah.minecities.entities.IInhabitant} already member of another city, returns null
     * @param owner {@link IInhabitant} owner of the city
     * @return {@link City} with owner set if successfull, otherwise null.
     */
    @Nullable City createCity(@NotNull IInhabitant owner);

    /**
     * Gets a {@link City} by its {@link UUID} (equal to {@link City#getId()})
     * @param id {@link UUID} of the {@link City}
     * @return {@link City} if found, otherwise null.
     */
    @Nullable City getCityById(@NotNull UUID id);

    @Nullable City getCityByPlayer(@NotNull IInhabitant inhabitant);

    /**
     * Adds an {@link IInhabitant} to the {@link City}.
     * If the {@link IInhabitant} already part of a different city, does not add, and returns false
     * @param city {@link City} to add {@link IInhabitant} to
     * @param inhabitant {@link IInhabitant} to add to the {@link City}
     * @return true if successfully added, otherwise false.
     */
    boolean addInhabitant(@NotNull City city, @NotNull IInhabitant inhabitant);

    /**
     * Sets the owner of a {@link City}. Overwrites current owner
     * @param city {@link City} to set owner
     * @param inhabitant {@link IInhabitant} to make owner
     */
    void setOwner(@NotNull City city, @NotNull IInhabitant inhabitant);

    /**
     * Removes a city.
     * @param city {@link City} to be removed
     */
    void destroyCity(@NotNull City city);

    /**
     * Method that ticks every 100 ticks
     * @param level level
     */
    void tickSlow(Level level);

    /**
     * Method that ticks every tick.
     * Should be used with care. Can impact performance hugely
     * @param level level
     */
    void tick(Level level);

    /**
     * Marks dirty, so it will be re-saved.
     */
    void markDirty();

    void removeUnassociatedBuilding(BlockPos pos);

    @Nullable City getCityByBuilding(BlockPos pos);

    void save();
}
