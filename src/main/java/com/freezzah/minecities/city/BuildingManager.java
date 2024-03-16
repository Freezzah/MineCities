package com.freezzah.minecities.city;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.building.*;
import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.blocks.building.townhall.TownhallBuilding;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.utils.BlockPosHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.freezzah.minecities.city.City.TAG_CITY_ID;

public class BuildingManager {

    private final List<IBuilding> buildings = new ArrayList<>();
    private final City city;
    private boolean isDirty = false;
    private CompoundTag buildingManagerTag;
    private final String TAG_BUILDINGS = "buildings";

    public BuildingManager(@NotNull City city) {
        this.city = city;
    }

    public boolean addBuilding(@NotNull IBuilding building) {
        boolean result = buildings.add(building);
        if(result)
            setDirty(true);
        return result;
    }

    public boolean removeBuilding(@NotNull IBuilding building) {
        boolean result = buildings.remove(building);
        if(result)
            setDirty(true);
        return result;
    }

    public @NotNull TownhallBuilding getTownhall(){
        for (IBuilding building: buildings) {
            if(building instanceof TownhallBuilding)
                return (TownhallBuilding) building;
        }
        return null;
    }

    public void tickSlow(Level level) {
        collectTaxes(level);
    }
    public void tick(Level level) {
        checkDirty();
    }
    private void collectTaxes(Level level) {
        int tax = buildings.size() * 10;
        for(IInhabitant inhabitant : city.getPlayers()) {
            Player player = level.getPlayerByUUID(inhabitant.getUUID());
            if(player != null){
                player.sendSystemMessage(Component.literal(
                        "Collected tax: " + tax));
            }
        }
    }

    /*
     * NBT Stuff
     */
    public void setDirty(boolean dirty) {
        this.isDirty = dirty;
        city.setDirty(dirty);
    }

    private void checkDirty() {
        if (isDirty) {
            this.refresh();
        }
    }

    private void refresh() {
        this.write();
    }

    public @NotNull CompoundTag getBuildingManagerTag() {
        try {
            if (this.buildingManagerTag == null || this.isDirty) {
                this.write();
            }
        } catch (final Exception e) {
            Constants.LOGGER.warn("Something went wrong persisting building manager of city: " + city.getId(), e);
        }
        return this.buildingManagerTag;
    }

    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();

        ListTag buildings = new ListTag();
        for(int i = 0; i < this.buildings.size(); i++){
            buildings.add(i, this.buildings.get(i).write());
        }
        tag.put(TAG_BUILDINGS, buildings);
        this.buildingManagerTag = tag;
        return tag;
    }

    public void read(@NotNull CompoundTag tag) {
        final ListTag buildingTagList = tag.getList(TAG_BUILDINGS, Tag.TAG_COMPOUND);
        for (int i = 0; i < buildingTagList.size(); ++i)
        {
            final CompoundTag buildingCompound = buildingTagList.getCompound(i);
            @Nullable final IBuilding b = createFrom(city, buildingCompound);
            this.buildings.add(b);
        }
    }

    private static IBuilding createFrom(final City city, CompoundTag tag) {

        final ResourceLocation type = new ResourceLocation(tag.getString(AbstractBuilding.TAG_BUILDING_TYPE));
        final BuildingEntry entry = ModBuildingRegistry.buildingRegistry.get(type);
        final BlockPos pos = BlockPosHelper.readBlockPos(tag);
        return entry.produceBuilding(city);
    }

    public IBuilding createFrom(final City city, final IBuildingBlock buildingBlock)
    {
        return this.createFrom(city, buildingBlock.getBuildingName());
    }

    public IBuilding createFrom(final City city, final ResourceLocation buildingName)
    {
        final BuildingEntry entry = ModBuildingRegistry.buildingRegistry.get(buildingName);
        return entry.produceBuilding(city);
    }
    public static @Nullable BuildingManager load(@NotNull CompoundTag tag, City city) {
        try {
            BuildingManager buildingManager = new BuildingManager(city);
            buildingManager.read(tag);
            return buildingManager;
        } catch (Exception e) {
            Constants.LOGGER.warn("Something went wrong loading the cities");
        }
        return null;
    }
}
