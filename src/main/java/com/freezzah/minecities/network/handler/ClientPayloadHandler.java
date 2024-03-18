package com.freezzah.minecities.network.handler;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.entities.IInhabitant;
import com.freezzah.minecities.entities.Inhabitant;
import com.freezzah.minecities.network.packet.UpdateEconomyPacket;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ClientPayloadHandler {
    private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

    public static ClientPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleData(final UpdateEconomyPacket data, final PlayPayloadContext context) {
        if(context.level().get().isClientSide) {
            City city = CityManager.getInstance().getCityById(data.cityUUID());
            city.getEconomyManager().setGold(data.gold());
        } else {
            Constants.LOGGER.info("test");
        }
    }
}
