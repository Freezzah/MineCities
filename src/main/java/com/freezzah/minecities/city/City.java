package com.freezzah.minecities.city;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.TownhallBuilding;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.entities.Inhabitant;
import com.freezzah.minecities.utils.ITaggable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class City implements ITaggable {
    private static final String TAG_CITY = "city";
    private static final String TAG_CITY_ID = "cityid";
    private static final String TAG_OWNER = "owner";
    private static final String TAG_PLAYERS = "players";
    private static final String TAG_PLAYER = "player";
    private static final String TAG_JOIN_DATE = "joinDate";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM");
//    private final List<IInhabitant> inhabitants = new ArrayList<>();
    private final Map<IInhabitant, LocalDateTime> inhabitants = new HashMap<>();
    private String name;
    private IInhabitant owner;
    private final BuildingManager buildingManager;
    private CompoundTag cityTag;
    private boolean isDirty = true;
    private UUID id;

    public City(@NotNull UUID id) {
        this.id = id;
        this.buildingManager = new BuildingManager(this);
    }

    public @NotNull UUID getId() {
        return this.id;
    }

    protected void setOwner(@NotNull IInhabitant inhabitant) {
        this.owner = inhabitant;
    }
    protected boolean addBuilding(@NotNull BlockPos pos) {
        return buildingManager.addBuilding(new TownhallBuilding(this, pos));
    }

    public @NotNull List<IInhabitant> getPlayers() {
        return inhabitants.keySet().stream().toList();
    }

    protected void addInhabitant(@NotNull IInhabitant inhabitant) {
        inhabitants.put(inhabitant, LocalDateTime.now());
    }

    public @NotNull TownhallBuilding getTownhall(){
        return buildingManager.getTownhall();
    }

    public @Nullable IBuilding getBuildingFromPos(@NotNull BlockPos pos) {
        return buildingManager.getBuildingFromPos(pos);
    }

    public void tick(Level level) {
        this.buildingManager.tick(level);
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
    }

    public @NotNull CompoundTag getCityTag() {
        try {
            if (this.cityTag == null || this.isDirty) {
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
        tag.putUUID(TAG_CITY_ID, this.id);
        tag.put(TAG_OWNER, owner.write());
        ListTag players = new ListTag();
        int i = 0;
        for(Map.Entry<IInhabitant, LocalDateTime> inhabitant: inhabitants.entrySet()) {
            CompoundTag playerTag = new CompoundTag();
            playerTag.put(TAG_PLAYER, inhabitant.getKey().write());
            playerTag.putString(TAG_JOIN_DATE, inhabitant.getValue().format(formatter));
            players.add(i, playerTag);
            i++;
        }
        tag.put(TAG_PLAYERS, players);
        this.cityTag = tag;
        return tag;
    }
    public void read(@NotNull CompoundTag tag) {
        this.id = tag.getUUID(TAG_CITY_ID);
        CompoundTag ownerTag = tag.getCompound(TAG_OWNER);
        setOwner(Inhabitant.load(ownerTag));
        ListTag players = tag.getList(TAG_PLAYERS, ListTag.TAG_COMPOUND);
        for(int i = 0; i < players.size(); i++) {
            CompoundTag playerTag = players.getCompound(i);
            inhabitants.put(Inhabitant.load(playerTag.getCompound(TAG_PLAYER)),
                    LocalDateTime.parse(playerTag.getString(TAG_JOIN_DATE), formatter));
        }
    }

    public static @Nullable City load(@NotNull CompoundTag tag) {
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

    public @NotNull FriendlyByteBuf putInFriendlyByteBuf(@NotNull FriendlyByteBuf friendlyByteBuf) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put(TAG_CITY, getCityTag());
        return friendlyByteBuf.writeNbt(compoundTag);
    }

}
