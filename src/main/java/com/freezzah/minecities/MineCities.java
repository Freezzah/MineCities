package com.freezzah.minecities;

import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.blocks.registry.ModBlock;
import com.freezzah.minecities.blocks.registry.ModBlockEntity;
import com.freezzah.minecities.client.gui.menu.ModMenuType;
import com.freezzah.minecities.event.EventHandler;
import com.freezzah.minecities.event.ModEventHandler;
import com.freezzah.minecities.items.ModItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.minecities.Constants.LOGGER;
import static com.freezzah.minecities.Constants.MOD_ID;

@Mod(MOD_ID)
public class MineCities {

    public MineCities(@NotNull IEventBus modEventBus) {
        LOGGER.info("MineCities: Starting loading");

        LOGGER.info("MineCities: Registering event listeners");
        modEventBus.register(new ModEventHandler());
        NeoForge.EVENT_BUS.register(new EventHandler());

        LOGGER.info("Setting up client event listener");
        modEventBus.addListener(ModEventHandler::clientSetup);

        LOGGER.info("MineCities: Registering items");
        ModItem.register(modEventBus);

        LOGGER.info("MineCities: Registering blocks");
        ModBlock.register(modEventBus);

        LOGGER.info("MineCities: Registering menu types");
        ModMenuType.register(modEventBus);

        LOGGER.info("MineCities: Registering mod registries");
        ModBuildingRegistry.register(modEventBus);

        LOGGER.info("MineCities: Registering mod block entities");
        ModBlockEntity.register(modEventBus);

        LOGGER.info("MineCities: Done Registering");
    }
}
