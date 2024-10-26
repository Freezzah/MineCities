package com.freezzah.minecities.network.handler;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.network.packet.UpdateEconomyPacket;
import com.freezzah.minecities.network.packet.UpdateFoodPacket;
import com.freezzah.minecities.network.packet.UpdateWastePacket;
import com.freezzah.minecities.network.packet.UpdateWaterPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class ClientPayloadHandler {
    private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

    public static ClientPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleData(final @NotNull UpdateEconomyPacket data, final @NotNull IPayloadContext context) {
        City city = CityManager.getInstance().getCityById(data.cityUUID());
        context.enqueueWork(() -> city.getEconomyManager().setGold(data.gold()));
    }

    public void handleData(final @NotNull UpdateWastePacket data, final @NotNull IPayloadContext context) {
        City city = CityManager.getInstance().getCityById(data.cityUUID());
        context.enqueueWork(() -> city.getWasteManager().setWaste(data.waste()));
    }

    public void handleData(final @NotNull UpdateWaterPacket data, final @NotNull IPayloadContext context) {
        City city = CityManager.getInstance().getCityById(data.cityUUID());
        context.enqueueWork(() -> city.getWaterManager().setWater(data.water()));
    }

    public void handleData(final @NotNull UpdateFoodPacket data, final @NotNull IPayloadContext context) {
        City city = CityManager.getInstance().getCityById(data.cityUUID());
        context.enqueueWork(() -> city.getFoodManager().setFood(data.food()));
    }
}
