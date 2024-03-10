package com.freezzah.minecities;

import com.freezzah.minecities.blocks.ModBlock;
import com.freezzah.minecities.client.gui.menu.ModMenuType;
import com.freezzah.minecities.event.EventHandler;
import com.freezzah.minecities.event.ModEventHandler;
import com.freezzah.minecities.items.ModItem;
import com.freezzah.minecities.network.handler.ModPacketHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.minecities.Constants.MOD_ID;
import static com.mojang.text2speech.Narrator.LOGGER;

@Mod(MOD_ID)
public class MineCities {

    public MineCities(@NotNull IEventBus modEventBus) {
        LOGGER.info("MineCities: Starting loading");

        LOGGER.info("MineCities: Registering listeners");
        modEventBus.register(new ModEventHandler());
        NeoForge.EVENT_BUS.register(new EventHandler());

        LOGGER.info("MineCities: Registring items");
        ModItem.register(modEventBus);
        LOGGER.info("MineCities: Registring blocks");
        ModBlock.register(modEventBus);
        LOGGER.info("MineCities: Registring menu types");
        ModMenuType.register(modEventBus);
        LOGGER.info("MineCities: Registring packet handler");
        modEventBus.register(new ModPacketHandler());

        LOGGER.info("MineCities: Done registring");
    }
}
