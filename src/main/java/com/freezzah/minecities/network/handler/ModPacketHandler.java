package com.freezzah.minecities.network.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModPacketHandler {
    @SubscribeEvent
    public void register(final RegisterPayloadHandlerEvent registerPayloadHandlerEvent) {
        final IPayloadRegistrar registrar = registerPayloadHandlerEvent.registrar(MOD_ID)
                .versioned("0.0.1")
                .optional();
    }
}
