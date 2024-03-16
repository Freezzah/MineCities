package com.freezzah.minecities.city;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.IBuildingBlock;
import com.freezzah.minecities.blocks.building.*;
import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.blocks.building.TownhallBuilding;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.tag.BuildingTags;
import com.freezzah.minecities.utils.BlockPosHelper;
import com.mojang.datafixers.util.Pair;
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

import java.util.HashMap;
import java.util.Map;

public class BuildingManager {

    private final Map<BlockPos, IBuilding> buildings = new HashMap<>();
    private final City city;
    private boolean isDirty = false;
    private CompoundTag buildingManagerTag;

    public BuildingManager(@NotNull City city) {
        this.city = city;
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

    public @NotNull TownhallBuilding getTownhall(){
        for (IBuilding building: buildings.values()) {
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
        int i = 0;
        for(Map.Entry<BlockPos, IBuilding> entry : this.buildings.entrySet()) {
            buildings.add(i, entry.getValue().write());
            i++;
        }
        tag.put(BuildingTags.TAG_BUILDINGS, buildings);
        this.buildingManagerTag = tag;
        return tag;
    }

    public void read(@NotNull CompoundTag tag) {
        final ListTag buildingTagList = tag.getList(BuildingTags.TAG_BUILDINGS, Tag.TAG_COMPOUND);
        for (int i = 0; i < buildingTagList.size(); ++i)
        {
            final CompoundTag buildingCompound = buildingTagList.getCompound(i);
            @Nullable final Pair<BlockPos, IBuilding> b = createFrom(city, buildingCompound);
            this.buildings.put(b.getFirst(), b.getSecond());
        }
    }

    private static Pair<BlockPos, IBuilding> createFrom(final City city, CompoundTag tag) {
        final ResourceLocation type = new ResourceLocation(tag.getString(BuildingTags.TAG_BUILDING_TYPE));
        final BuildingEntry entry = ModBuildingRegistry.buildingRegistry.get(type);
        final BlockPos pos = BlockPosHelper.readBlockPos(tag);
        IBuilding building = entry.produceBuilding(city);
        building.read(tag);
        Pair<BlockPos, IBuilding> pair = new Pair<>(pos, building);
        return pair;
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
