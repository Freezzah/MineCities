package com.freezzah.minecities.event;

import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.blocks.block.registry.ModBlock;
import com.freezzah.minecities.client.gui.menu.ModMenuType;
import com.freezzah.minecities.client.gui.screen.BankScreen;
import com.freezzah.minecities.client.gui.screen.CityOverviewScreen;
import com.freezzah.minecities.network.handler.ClientPayloadHandler;
import com.freezzah.minecities.network.packet.UpdateEconomyPacket;
import com.freezzah.minecities.network.packet.UpdateWastePacket;
import com.freezzah.minecities.network.packet.UpdateWaterPacket;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModEventHandler {

    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuType.BANK_MENU.get(), BankScreen::new);
            MenuScreens.register(ModMenuType.CITY_OVERVIEW_MENU.get(), CityOverviewScreen::new);
        });
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
    public void onRegisterPayloads(RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(MOD_ID);
        registrar.play(UpdateEconomyPacket.ID, UpdateEconomyPacket::new, ClientPayloadHandler.getInstance()::handleData);
        registrar.play(UpdateWastePacket.ID, UpdateWastePacket::new, ClientPayloadHandler.getInstance()::handleData);
        registrar.play(UpdateWaterPacket.ID, UpdateWaterPacket::new, ClientPayloadHandler.getInstance()::handleData);
    }
}