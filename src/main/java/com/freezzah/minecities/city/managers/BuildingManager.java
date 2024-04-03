package com.freezzah.minecities.city.managers;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.AbstractBuildingBlock;
import com.freezzah.minecities.blocks.IBuildingBlock;
import com.freezzah.minecities.blocks.building.AbstractBuilding;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.TownhallBuilding;
import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.extensions.IWaterGeneratorNearby;
import com.freezzah.minecities.tag.BuildingTags;
import com.freezzah.minecities.tag.CityTags;
import com.freezzah.minecities.utils.BlockPosHelper;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.DistanceManager;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingManager extends AbstractCityManager {

    private final Map<BlockPos, IBuilding> buildings = new HashMap<>();

    public BuildingManager(@NotNull City city) {
        super(city);
    }

    @Contract("_, _, _ -> new")
    private static @NotNull Pair<BlockPos, IBuilding> createFrom(final City city, @NotNull CompoundTag buildingTag, @NotNull CompoundTag buildingPosTag) {
        final ResourceLocation type = new ResourceLocation(buildingTag.getString(BuildingTags.TAG_BUILDING_TYPE));
        final BuildingEntry entry = ModBuildingRegistry.buildingRegistry.get(type);
        final BlockPos pos = BlockPosHelper.readBlockPos(buildingPosTag);
        IBuilding building = entry.produceBuilding(city);
        building.read(buildingTag);
        return new Pair<>(pos, building);
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

    public boolean addBuilding(@NotNull BlockPos pos, @NotNull IBuilding building) {
        buildings.put(pos, building);
        setDirty(true);
        return true;
    }

    /*
     * NBT Stuff
     */

    public boolean removeBuilding(@NotNull BlockPos blockPos) {
        buildings.remove(blockPos);
        setDirty(true);
        return true;
    }

    public @Nullable TownhallBuilding getTownhall() {
        for (IBuilding building : buildings.values()) {
            if (building instanceof TownhallBuilding)
                return (TownhallBuilding) building;
        }
        return null;
    }

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();

        ListTag buildings = new ListTag();
        ListTag blockPos = new ListTag();
        int i = 0;
        for (Map.Entry<BlockPos, IBuilding> entry : this.buildings.entrySet()) {
            buildings.add(i, entry.getValue().write());
            blockPos.add(i, BlockPosHelper.writeBlockPos(new CompoundTag(), entry.getKey()));
            i++;
        }
        tag.put(CityTags.TAG_BUILDINGS, buildings);
        tag.put(CityTags.TAG_BUILDINGS_POS, blockPos);
        this.setManagerTag(tag);
        return tag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {
        final ListTag buildingTagList = tag.getList(CityTags.TAG_BUILDINGS, Tag.TAG_COMPOUND);
        final ListTag blockPosTagList = tag.getList(CityTags.TAG_BUILDINGS_POS, Tag.TAG_COMPOUND);
        for (int i = 0; i < buildingTagList.size(); ++i) {
            final CompoundTag buildingCompound = buildingTagList.getCompound(i);
            final CompoundTag posCompound = blockPosTagList.getCompound(i);
            @Nullable final Pair<BlockPos, IBuilding> b = createFrom(getCity(), buildingCompound, posCompound);
            this.buildings.put(b.getFirst(), b.getSecond());
        }
    }

    public IBuilding createFrom(final City city, final @NotNull IBuildingBlock buildingBlock) {
        return this.createFrom(city, buildingBlock.getBuildingName());
    }

    public IBuilding createFrom(final City city, final ResourceLocation buildingName) {
        final BuildingEntry entry = ModBuildingRegistry.buildingRegistry.get(buildingName);
        return entry.produceBuilding(city);
    }

    public @Nullable IBuilding getBuildingByPos(@NotNull BlockPos pos) {
        return buildings.get(pos);
    }

    public List<IBuilding> getBuildings() {
        return buildings.values().stream().toList();
    }

    public void addBuilding(BlockPos pos, AbstractBuildingBlock abstractBuildingBlock) {
        this.addBuilding(pos, createFrom(getCity(), abstractBuildingBlock));
        setDirty(true);
    }

    public @Nullable BlockPos getPosByBuilding(IBuilding building) {
        for(Map.Entry<BlockPos, IBuilding> entry : this.buildings.entrySet()) {
            if(entry.getValue().equals(building)){
                return entry.getKey();
            }
        }
        return null;
    }

    public <T, Y> List<? extends IBuilding> getBuildingsWithinRange(T building, int i, Class<Y> type) {
        List<IBuilding> buildings = new ArrayList<>();
        if(building instanceof IBuilding iBuilding) {
            BlockPos pos = getPosByBuilding(iBuilding);
            for(IBuilding building1 : getBuildings()) {
                if(type.isAssignableFrom(building1.getClass())) {
                    BlockPos pos1 = getPosByBuilding(building1);
                    double dist = BlockPosHelper.distance(pos, pos1);
                    if(dist < i){
                        buildings.add(building1);
                    }
                }
            }
        }
        return buildings;

    }
}
