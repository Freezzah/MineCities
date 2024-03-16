package com.freezzah.minecities.event;

import com.freezzah.minecities.blocks.registry.ModBlock;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.jetbrains.annotations.NotNull;

public class ModEventHandler {
    @SuppressWarnings("unused")
    @SubscribeEvent
    public void createTabBuildContent(@NotNull BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModBlock.TOWNHALL_BLOCK.get());
            event.accept(ModBlock.BANK_BLOCK.get());
        }
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void registerRegistries(@NotNull NewRegistryEvent event) {
        event.register(ModBuildingRegistry.buildingRegistry);
    }
}