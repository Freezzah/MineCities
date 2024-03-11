package com.freezzah.minecities.city;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.TownhallBuilding;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.entities.Inhabitant;
import com.freezzah.minecities.utils.ITaggable;
import com.freezzah.minecities.utils.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.*;

public class City implements ITaggable {
    private static final String TAG_CITY = "city";
    private static final String TAG_CITY_ID = "cityid";
    private static final String TAG_OWNER = "owner";
    private static final String TAG_PLAYERS = "players";
    private final List<IInhabitant> inhabitants = new ArrayList<>();
    private final Map<IInhabitant, LocalDateTime> joinDate = new HashMap<>();
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
        return inhabitants;
    }

    protected void addInhabitant(@NotNull IInhabitant inhabitant) {
        inhabitants.add(inhabitant);
    }

    public @NotNull TownhallBuilding getTownhall(){
        return buildingManager.getTownhall();
    }

    public @Nullable IBuilding getBuildingFromPos(@NotNull BlockPos pos) {
        return buildingManager.getBuildingFromPos(pos);
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
        tag.put(TAG_PLAYERS, inhabitants.stream().map(IInhabitant::write).collect(NBTHelper.toListNBT()));
        this.cityTag = tag;
        return tag;
    }
    public void read(@NotNull CompoundTag tag) {
        this.id = tag.getUUID(TAG_CITY_ID);
        CompoundTag ownerTag = tag.getCompound(TAG_OWNER);
        setOwner(Inhabitant.load(ownerTag));
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
