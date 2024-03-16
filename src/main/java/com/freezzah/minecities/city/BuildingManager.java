package com.freezzah.minecities.city;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.building.AbstractBuilding;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.TownhallBuilding;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.entities.Inhabitant;
import com.freezzah.minecities.utils.BlockPosHelper;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.ScoreAccess;
import net.minecraft.world.scores.ScoreHolder;
import net.minecraft.world.scores.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public void tick(Level level) {
        collectTaxes(level);
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
    }

    private void checkDirty() {
        if (isDirty) {
            this.refresh();
        }
    }

    private void refresh() {
        this.write();
    }

    public @NotNull CompoundTag getCityTag() {
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
        for(int i = 0; i < buildings.size(); i++){
            buildings.add(this.buildings.get(i).write());
        }
        tag.put(TAG_BUILDINGS, buildings);
        this.buildingManagerTag = tag;
        return tag;
    }
    public void read(@NotNull CompoundTag tag) {
        ListTag buidlings = tag.getList(TAG_BUILDINGS, Tag.TAG_COMPOUND);
        for(int i = 0; i < buidlings.size(); i++) {
            CompoundTag compoundTag = buidlings.getCompound(i);
            this.buildings.add(AbstractBuilding.read(compoundTag));
        }
    }

    public static @Nullable BuildingManager load(@NotNull CompoundTag tag, City city) {
        try {
            UUID id = tag.getUUID(TAG_CITY_ID);
            City city = new City(id);
            city.read(tag);
            return city;
        } catch (Exception e) {
            Constants.LOGGER.warn("Something went wrong loading the cities");
        }
        return null;
    }

}
