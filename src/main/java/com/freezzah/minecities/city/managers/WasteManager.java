package com.freezzah.minecities.city.managers;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.extensions.IWasteGenerator;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.network.packet.UpdateWastePacket;
import com.freezzah.minecities.tag.CityTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WasteManager extends AbstractCityManager {
    long waste = 0;

    public WasteManager(City city) {
        super(city);
    }

    public static @Nullable WasteManager load(@NotNull CompoundTag tag, City city) {
        try {
            WasteManager wasteManager = new WasteManager(city);
            wasteManager.read(tag);
            return wasteManager;
        } catch (Exception e) {
            Constants.LOGGER.warn("Something went wrong loading the cities");
        }
        return null;
    }

    public long getWaste() {
        return waste;
    }

    public void setWaste(long waste) {
        this.waste = waste;
    }

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.putLong(CityTags.TAG_WASTE, waste);
        setManagerTag(tag);
        return tag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {
        this.waste = tag.getLong(CityTags.TAG_WASTE);
    }

    @Override
    public void tickSlow(@NotNull Level level) {
        super.tickSlow(level);
        for (IBuilding building : getCity().getBuildingManager().getBuildings()) {
            if (building instanceof IWasteGenerator wasteGenerator) {
                waste += wasteGenerator.generateWaste();
            }
            setDirty(true);
            for (IInhabitant inhabitant : getCity().getPlayers()) {
                Player player = level.getPlayerByUUID(inhabitant.getUUID());
                if (player instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.sendToPlayer(serverPlayer, new UpdateWastePacket(waste, getCity().getId()));
                }
            }
        }
    }
}
