package com.freezzah.minecities;

import com.freezzah.minecities.blocks.ModBlock;
import com.freezzah.minecities.client.gui.menu.ModMenuType;
import com.freezzah.minecities.event.ModEventHandler;
import com.freezzah.minecities.items.ModItem;
import com.freezzah.minecities.network.handler.ModPacketHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import static com.freezzah.minecities.Constants.MOD_ID;
import static com.mojang.text2speech.Narrator.LOGGER;

@Mod(MOD_ID)
public class MineCities {
    public MineCities(IEventBus modEventBus) {
        LOGGER.info("Municipality: Starting loading");

        LOGGER.info("Municipality: Registering listeners");
        modEventBus.register(new ModEventHandler());

        LOGGER.info("Municipality: Registring items");
        ModItem.register(modEventBus);
        LOGGER.info("Municipality: Registring blocks");
        ModBlock.register(modEventBus);
        LOGGER.info("Municipality: Registring menu types");
        ModMenuType.register(modEventBus);
        LOGGER.info("Municipality: Registring packet handler");
        modEventBus.register(new ModPacketHandler());

        LOGGER.info("Municipality: Done registring");
    }
}
