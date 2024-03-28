package com.freezzah.minecities.city.managers;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.extensions.IWaterConsumer;
import com.freezzah.minecities.city.extensions.IWaterGenerator;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.network.packet.UpdateWaterPacket;
import com.freezzah.minecities.tag.CityTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaterManager extends AbstractCityManager {
    long water = 0;

    public WaterManager(City city) {
        super(city);
    }

    public static @Nullable WaterManager load(@NotNull CompoundTag tag, City city) {
        try {
            WaterManager waterManager = new WaterManager(city);
            waterManager.read(tag);
            return waterManager;
        } catch (Exception e) {
            Constants.LOGGER.warn("Something went wrong loading the cities");
        }
        return null;
    }

    public long getWater() {
        return water;
    }

    public void setWater(long water) {
        this.water = water;
    }

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.putLong(CityTags.TAG_WATER, water);
        setManagerTag(tag);
        return tag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {
        this.water = tag.getLong(CityTags.TAG_WATER);
    }

    @Override
    public void tickSlow(Level level) {
        super.tickSlow(level);
        for (IBuilding building : getCity().getBuildingManager().getBuildings()) {
            if (building instanceof IWaterGenerator waterGenerator)
                water += waterGenerator.generateWater();
            if (building instanceof IWaterConsumer waterConsumer) {
                water -= waterConsumer.consumeWater(this.getWater());
            }
            setDirty(true);
            for (IInhabitant inhabitant : getCity().getPlayers()) {
                Player player = level.getPlayerByUUID(inhabitant.getUUID());
                if (player instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.PLAYER.with(serverPlayer).send(new UpdateWaterPacket(water, getCity().getId()));
                }
            }
        }
    }
}
