package com.freezzah.minecities.city.managers;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.IBuildingBlock;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.TownhallBuilding;
import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.tag.BuildingTags;
import com.freezzah.minecities.tag.CityTags;
import com.freezzah.minecities.utils.BlockPosHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingManager extends AbstractCityManager{

    private final Map<BlockPos, IBuilding> buildings = new HashMap<>();

    public BuildingManager(@NotNull City city) {
        super(city);
    }

    public boolean addBuilding(@NotNull BlockPos pos, @NotNull IBuilding building) {
        buildings.put(pos, building);
        setDirty(true);
        return true;
    }

    public boolean removeBuilding(@NotNull BlockPos blockPos) {
        buildings.remove(blockPos);
        setDirty(true);
        return true;
    }

    public @Nullable TownhallBuilding getTownhall(){
        for (IBuilding building: buildings.values()) {
            if(building instanceof TownhallBuilding)
                return (TownhallBuilding) building;
        }
        return null;
    }

    /*
     * NBT Stuff
     */

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();

        ListTag buildings = new ListTag();
        int i = 0;
        for(Map.Entry<BlockPos, IBuilding> entry : this.buildings.entrySet()) {
            buildings.add(i, entry.getValue().write());
            i++;
        }
        tag.put(CityTags.TAG_BUILDINGS, buildings);
        this.setManagerTag(tag);
        return tag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {
        final ListTag buildingTagList = tag.getList(CityTags.TAG_BUILDINGS, Tag.TAG_COMPOUND);
        for (int i = 0; i < buildingTagList.size(); ++i)
        {
            final CompoundTag buildingCompound = buildingTagList.getCompound(i);
            @Nullable final Pair<BlockPos, IBuilding> b = createFrom(getCity(), buildingCompound);
            this.buildings.put(b.getFirst(), b.getSecond());
        }
    }

    @Contract("_, _ -> new")
    private static @NotNull Pair<BlockPos, IBuilding> createFrom(final City city, @NotNull CompoundTag tag) {
        final ResourceLocation type = new ResourceLocation(tag.getString(BuildingTags.TAG_BUILDING_TYPE));
        final BuildingEntry entry = ModBuildingRegistry.buildingRegistry.get(type);
        final BlockPos pos = BlockPosHelper.readBlockPos(tag);
        IBuilding building = entry.produceBuilding(city);
        building.read(tag);
        return new Pair<>(pos, building);
    }

    public IBuilding createFrom(final City city, final @NotNull IBuildingBlock buildingBlock)
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

    public @Nullable IBuilding getBuildingByPos(@NotNull BlockPos pos) {
        return buildings.get(pos);
    }

    public List<IBuilding> getBuildings() {
        return buildings.values().stream().toList();
    }
}
