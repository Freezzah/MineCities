package com.freezzah.minecities.city;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.TownhallBuilding;
import com.freezzah.minecities.city.managers.BuildingManager;
import com.freezzah.minecities.city.managers.EconomyManager;
import com.freezzah.minecities.city.managers.WasteManager;
import com.freezzah.minecities.city.managers.WaterManager;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.entities.Inhabitant;
import com.freezzah.minecities.tag.CityTags;
import com.freezzah.minecities.utils.ITaggable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class City implements ITaggable {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//    private final List<IInhabitant> inhabitants = new ArrayList<>();
    private final Map<IInhabitant, LocalDateTime> inhabitants = new HashMap<>();
    private String name;
    private IInhabitant owner;
    private BuildingManager buildingManager;
    private EconomyManager economyManager;
    private WasteManager wasteManager;
    private WaterManager waterManager;
    private CompoundTag cityTag;
    private boolean isDirty = true;
    private UUID id;

    public City(@NotNull UUID id) {
        this.id = id;
        this.buildingManager = new BuildingManager(this);
        this.economyManager = new EconomyManager(this);
        this.wasteManager = new WasteManager(this);
        this.waterManager = new WaterManager(this);
        setDirty(true);
    }

    public @NotNull UUID getId() {
        return this.id;
    }

    protected void setOwner(@NotNull IInhabitant inhabitant) {
        this.owner = inhabitant;
        setDirty(true);
    }

    public boolean isOwner(@NotNull IInhabitant inhabitant) {
        return this.owner.equals(inhabitant);
    }

    protected boolean addBuilding(@NotNull BlockPos pos, @NotNull IBuilding building) {
        boolean result = buildingManager.addBuilding(pos, building);
        if(result)
            setDirty(true);
        return result;
    }

    public void removeBuilding(@NotNull BlockPos pos) {
        boolean result = buildingManager.removeBuilding(pos);
        if(result)
            setDirty(true);
    }

    public @NotNull List<IInhabitant> getPlayers() {
        return inhabitants.keySet().stream().toList();
    }

    protected void addInhabitant(@NotNull IInhabitant inhabitant) {
        inhabitants.put(inhabitant, LocalDateTime.now());
        setDirty(true);
    }

    public @Nullable TownhallBuilding getTownhall(){
        return buildingManager.getTownhall();
    }

    public void tickSlow(Level level) {
        this.buildingManager.tickSlow(level);
        this.economyManager.tickSlow(level);
        this.waterManager.tickSlow(level);
        this.wasteManager.tickSlow(level);
    }

    public void tick(Level level) {
        checkDirty();
        this.buildingManager.tick(level);
        this.economyManager.tick(level);
        this.waterManager.tick(level);
        this.wasteManager.tick(level);
    }

    public @NotNull BuildingManager getBuildingManager() {
        return buildingManager;
    }

    public @NotNull  EconomyManager getEconomyManager() {
        return economyManager;
    }

    public @NotNull WasteManager getWasteManager() {
        return wasteManager;
    }

    public @NotNull WaterManager getWaterManager() { return waterManager;
    }
    /*
     * NBT related methods
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
        CityManager.getInstance().markDirty();
        setDirty(false);
    }

    public @NotNull CompoundTag getCityTag() {
        try {
            checkDirty();
            if (this.cityTag == null || isDirty) {
                this.write();
            }
        } catch (final Exception e) {
            Constants.LOGGER.warn("Something went wrong persisting colony: " + id, e);
        }
        return this.cityTag;
    }

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID(CityTags.TAG_CITY_ID, this.id);
        tag.put(CityTags.TAG_OWNER, owner.write());
        ListTag players = new ListTag();
        int i = 0;
        for(Map.Entry<IInhabitant, LocalDateTime> inhabitant: inhabitants.entrySet()) {
            CompoundTag playerTag = new CompoundTag();
            playerTag.put(CityTags.TAG_PLAYER, inhabitant.getKey().write());
            playerTag.putString(CityTags.TAG_JOIN_DATE, inhabitant.getValue().format(formatter));
            players.add(i, playerTag);
            i++;
        }
        tag.put(CityTags.TAG_PLAYERS, players);
        tag.put(CityTags.TAG_BUILDING_MANAGER, buildingManager.write());
        tag.put(CityTags.TAG_ECONOMY_MANAGER, economyManager.write());
        tag.put(CityTags.TAG_WASTE_MANAGER, wasteManager.write());
        tag.put(CityTags.TAG_WATER_MANAGER, waterManager.write());
        this.cityTag = tag;
        return tag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {
        this.id = tag.getUUID(CityTags.TAG_CITY_ID);
        CompoundTag ownerTag = tag.getCompound(CityTags.TAG_OWNER);
        setOwner(Inhabitant.load(ownerTag));
        ListTag players = tag.getList(CityTags.TAG_PLAYERS, ListTag.TAG_COMPOUND);
        for(int i = 0; i < players.size(); i++) {
            CompoundTag playerTag = players.getCompound(i);
            inhabitants.put(Inhabitant.load(playerTag.getCompound(CityTags.TAG_PLAYER)),
                    LocalDateTime.parse(playerTag.getString(CityTags.TAG_JOIN_DATE), formatter));
        }
        this.buildingManager = BuildingManager.load(tag.getCompound(CityTags.TAG_BUILDING_MANAGER), this);
        this.economyManager = EconomyManager.load(tag.getCompound(CityTags.TAG_ECONOMY_MANAGER), this);
        this.wasteManager = WasteManager.load(tag.getCompound(CityTags.TAG_WASTE_MANAGER), this);
        this.waterManager = WaterManager.load(tag.getCompound(CityTags.TAG_WATER_MANAGER), this);
    }

    public static @Nullable City load(@NotNull CompoundTag tag) {
        try {
            UUID id = tag.getUUID(CityTags.TAG_CITY_ID);
            City city = new City(id);
            city.read(tag);
            return city;
        } catch (Exception e) {
            Constants.LOGGER.warn("Something went wrong loading the cities");
        }
        return null;
    }

    public String getCityName() {
        return "mytempcity";
    }

    public @NotNull FriendlyByteBuf putInFriendlyByteBuf(@NotNull FriendlyByteBuf friendlyByteBuf) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put(CityTags.TAG_CITY, getCityTag());
        return friendlyByteBuf.writeNbt(compoundTag);
    }
}
