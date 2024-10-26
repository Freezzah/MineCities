package com.freezzah.minecities.event;

import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.blocks.block.registry.ModBlock;
import com.freezzah.minecities.client.gui.menu.ModMenuType;
import com.freezzah.minecities.client.gui.screen.BankScreen;
import com.freezzah.minecities.client.gui.screen.CityOverviewScreen;
import com.freezzah.minecities.network.handler.ClientPayloadHandler;
import com.freezzah.minecities.network.packet.UpdateEconomyPacket;
import com.freezzah.minecities.network.packet.UpdateFoodPacket;
import com.freezzah.minecities.network.packet.UpdateWastePacket;
import com.freezzah.minecities.network.packet.UpdateWaterPacket;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.jetbrains.annotations.NotNull;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModEventHandler {
    public static void clientSetup(final RegisterMenuScreensEvent event) {
        event.register(ModMenuType.BANK_MENU.get(), BankScreen::new);
        event.register(ModMenuType.CITY_OVERVIEW_MENU.get(), CityOverviewScreen::new);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void createTabBuildContent(@NotNull BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModBlock.TOWNHALL_BLOCK.get());
            event.accept(ModBlock.BANK_BLOCK.get());
            event.accept(ModBlock.HOUSE_BLOCK.get());
            event.accept(ModBlock.WATER_COLLECTOR_BLOCK.get());
            event.accept(ModBlock.FARM_BLOCK.get());
            event.accept(ModBlock.WELL_BLOCK.get());
        }
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void registerRegistries(@NotNull NewRegistryEvent event) {
        event.register(ModBuildingRegistry.buildingRegistry);
    }


    @SubscribeEvent
    public void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MOD_ID);
        registrar.playToClient(UpdateEconomyPacket.TYPE, UpdateEconomyPacket.STREAM_CODEC, ClientPayloadHandler.getInstance()::handleData);
        registrar.playToClient(UpdateWastePacket.TYPE, UpdateWastePacket.STREAM_CODEC, ClientPayloadHandler.getInstance()::handleData);
        registrar.playToClient(UpdateWaterPacket.TYPE, UpdateWaterPacket.STREAM_CODEC, ClientPayloadHandler.getInstance()::handleData);
        registrar.playToClient(UpdateFoodPacket.TYPE, UpdateFoodPacket.STREAM_CODEC, ClientPayloadHandler.getInstance()::handleData);
    }
}