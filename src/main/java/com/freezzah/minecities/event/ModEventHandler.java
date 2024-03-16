package com.freezzah.minecities.event;

import com.freezzah.minecities.blocks.ModBlock;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;

public class ModEventHandler {
    @SubscribeEvent
    public void createTabBuildContent(@NotNull BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModBlock.TOWNHALL_BLOCK.get());
            event.accept(ModBlock.BANK_BLOCK.get());
        }
    }

}