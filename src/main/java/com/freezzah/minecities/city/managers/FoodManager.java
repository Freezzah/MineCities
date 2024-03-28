package com.freezzah.minecities.city.managers;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.extensions.IFoodConsumer;
import com.freezzah.minecities.city.extensions.IFoodGenerator;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.network.packet.UpdateFoodPacket;
import com.freezzah.minecities.tag.CityTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FoodManager extends AbstractCityManager {
    double food = 0;

    public FoodManager(City city) {
        super(city);
    }

    public static @Nullable FoodManager load(@NotNull CompoundTag tag, City city) {
        try {
            FoodManager foodManager = new FoodManager(city);
            foodManager.read(tag);
            return foodManager;
        } catch (Exception e) {
            Constants.LOGGER.warn("Something went wrong loading the cities");
        }
        return null;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble(CityTags.TAG_FOOD, food);
        setManagerTag(tag);
        return tag;
    }

    @Override
    public void read(@NotNull CompoundTag tag) {
        this.food = tag.getDouble(CityTags.TAG_FOOD);
    }

    @Override
    public void tickSlow(Level level) {
        super.tickSlow(level);
        for (IBuilding building : getCity().getBuildingManager().getBuildings()) {
            if (building instanceof IFoodGenerator foodGenerator)
                food += foodGenerator.generateFood();
            if (building instanceof IFoodConsumer foodConsumer) {
                food -= foodConsumer.consumeFood(this.getFood());
            }
            setDirty(true);
            for (IInhabitant inhabitant : getCity().getPlayers()) {
                Player player = level.getPlayerByUUID(inhabitant.getUUID());
                if (player instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.PLAYER.with(serverPlayer).send(new UpdateFoodPacket(food, getCity().getId()));
                }
            }
        }
    }
}
