package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.blocks.building.registry.BuildingEntry;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.exception.UpgradeException;
import com.freezzah.minecities.tag.BuildingTags;
import com.freezzah.minecities.utils.Requirement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;


public abstract class AbstractBuilding implements IBuilding {
    private BuildingEntry buildingType = null;
    private byte buildingLevel = 1;

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.putString(BuildingTags.TAG_BUILDING_TYPE, this.getBuildingType().getRegistryName().toString());
        tag.putByte(BuildingTags.TAG_BUILDING_LEVEL, this.buildingLevel);
        return tag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {
        this.buildingLevel = tag.getByte(BuildingTags.TAG_BUILDING_LEVEL);
    }

    @Override
    public final BuildingEntry getBuildingType() {
        return this.buildingType;
    }

    @Override
    public void setBuildingType(final BuildingEntry buildingType) {
        this.buildingType = buildingType;
    }

    @Override
    public byte getBuildingLevel() {
        return this.buildingLevel;
    }

    @Override
    public byte getMaxLevel() {
        return (byte) (getRequirements().size());
    }

    @Override
    public boolean increaseLevel(ServerLevel level, IInhabitant initiator) {
        try {
            City city = CityManager.getInstance().getCityByPlayer(initiator);
            if (city == null) {
                return false;
            }
            if (canUpgrade((byte) (this.getBuildingLevel() + 1), city)) {
                boolean moneyTaken = withdrawMaterials((byte) (this.getBuildingLevel() + 1), city, true);
                if (moneyTaken) {
                    this.buildingLevel = (byte) Math.min(getBuildingLevel() + 1, getMaxLevel());
                    city.getBuildingManager().setDirty(true);
                    return true;
                } else {
                    throw new UpgradeException("Not enough gold");
                }
            }
        } catch (UpgradeException upgradeException) {
            Objects.requireNonNull(level.getPlayerByUUID(initiator.getUUID())).displayClientMessage(Component.literal(upgradeException.getMessage()), false);
        }
        return false;
    }

    private boolean canUpgrade(byte desiredLevel, City city) throws UpgradeException {
        if (desiredLevel > getMaxLevel())
            throw new UpgradeException("Max level reached");
        if (!checkGold(desiredLevel, city))
            throw new UpgradeException("Not enough gold");
        if (!checkLevelRequirements(desiredLevel))
            throw new UpgradeException("Requirements not met");
        return true;
    }

    private boolean checkGold(byte desiredLevel, City city) {
        return withdrawMaterials(desiredLevel, city, false);
    }

    private boolean withdrawMaterials(byte desiredLevel, @NotNull City city, boolean performTake) {
        long requiredGold = getRequirements().get(desiredLevel - 1).gold();
        return city.getEconomyManager().tryTakeGold(requiredGold, performTake);
    }

    abstract @NotNull List<Requirement> getRequirements();

    public abstract boolean checkLevelRequirements(byte desiredLevel);
}
