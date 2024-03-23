package com.freezzah.minecities.network.handler;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.network.packet.UpdateEconomyPacket;
import com.freezzah.minecities.network.packet.UpdateWastePacket;
import com.freezzah.minecities.network.packet.UpdateWaterPacket;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import org.jetbrains.annotations.NotNull;

public class ClientPayloadHandler {
    private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

    public static ClientPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleData(final @NotNull UpdateEconomyPacket data, final @NotNull PlayPayloadContext context) {
        City city = CityManager.getInstance().getCityById(data.cityUUID());
        context.workHandler().submitAsync(() -> city.getEconomyManager().setGold(data.gold()));
    }

    public void handleData(final @NotNull UpdateWastePacket data, final @NotNull PlayPayloadContext context) {
        City city = CityManager.getInstance().getCityById(data.cityUUID());
        context.workHandler().submitAsync(() -> city.getWasteManager().setWaste(data.waste()));
    }

    public void handleData(final @NotNull UpdateWaterPacket data, final @NotNull PlayPayloadContext context) {
        City city = CityManager.getInstance().getCityById(data.cityUUID());
        context.workHandler().submitAsync(() -> city.getWaterManager().setWater(data.water()));
    }
}
