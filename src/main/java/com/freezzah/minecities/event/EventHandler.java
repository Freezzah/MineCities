package com.freezzah.minecities.event;

import com.freezzah.minecities.blocks.TownhallBlock;
import com.freezzah.minecities.city.CityManager;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class EventHandler {
    @SubscribeEvent
    public void onPlaceEvent(@NotNull BlockEvent.EntityPlaceEvent event) {
        if(event.getPlacedBlock().getBlock() instanceof TownhallBlock) {
            if(event.getEntity().level() instanceof ServerLevel serverLevel){
                CityManager.getInstance().createCity(UUID.randomUUID());
            }
        }
    }

    @SubscribeEvent
    public void levelLoad(LevelEvent.@NotNull Load event){
        if(event.getLevel() instanceof ServerLevel serverLevel) {
            CityManager manager = new CityManager(serverLevel);
        }
    }
}
