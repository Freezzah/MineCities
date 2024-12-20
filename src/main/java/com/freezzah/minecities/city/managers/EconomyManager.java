package com.freezzah.minecities.city.managers;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.extensions.ILiveable;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.network.packet.UpdateEconomyPacket;
import com.freezzah.minecities.tag.CityTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EconomyManager extends AbstractCityManager {
    private long gold;

    public EconomyManager(City city) {
        super(city);
    }

    public static @Nullable EconomyManager load(@NotNull CompoundTag tag, City city) {
        try {
            EconomyManager economyManager = new EconomyManager(city);
            economyManager.read(tag);
            return economyManager;
        } catch (Exception e) {
            Constants.LOGGER.warn("Something went wrong loading the cities");
        }
        return null;
    }

    /*
     * NBT Stuff
     */
    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.putLong(CityTags.TAG_MONEY, gold);
        setManagerTag(tag);
        return tag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {
        this.gold = tag.getLong(CityTags.TAG_MONEY);
    }

    @Override
    public void tickSlow(@NotNull Level level) {
        for (IBuilding building : getCity().getBuildingManager().getBuildings()) {
            if (building instanceof ILiveable taxableBuilding) {
                gold += taxableBuilding.collectTax();
            }
            setDirty(true);
            for (IInhabitant inhabitant : getCity().getPlayers()) {
                Player player = level.getPlayerByUUID(inhabitant.getUUID());
                if (player instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.sendToPlayer(serverPlayer, new UpdateEconomyPacket(gold, getCity().getId()));
                }
            }
        }
    }

    public long getGold() {
        return this.gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
        setDirty(true);
    }

    public void addGold(int count) {
        this.gold += count;
        setDirty(true);
    }

    public long withdrawStack() {
        long count = (Math.min(gold, 256));
        this.gold -= count;
        setDirty(true);
        return count;
    }

    public boolean tryTakeGold(long requiredGold, boolean performTake) {
        if (requiredGold > gold)
            return false;
        else {
            if (performTake) {
                this.gold -= requiredGold;
                setDirty(true);
            }
            return true;
        }
    }
}
