package com.freezzah.minecities.event;

import com.freezzah.minecities.blocks.TownhallBlock;
import com.freezzah.minecities.blocks.building.IBuildingBlock;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.entities.Inhabitant;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.jetbrains.annotations.NotNull;

public class EventHandler {

    //TODO
    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onPlaceEvent(@NotNull BlockEvent.EntityPlaceEvent event) {
        if(event.getPlacedBlock().getBlock() instanceof IBuildingBlock iBuildingBlock) {
            if(iBuildingBlock instanceof TownhallBlock townhallBlock) {
                if (event.getEntity().level() instanceof ServerLevel serverLevel) {
                    if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                        City city = CityManager.getInstance().getCityByPlayer(Inhabitant.fromPlayer(serverPlayer));
                        if(city != null){
                            event.setCanceled(true);
                            return;
                        }
                        city = CityManager.getInstance().createCity(Inhabitant.fromPlayer(serverPlayer));
                        CityManager.getInstance().addBuilding(city, event.getPos());
                    }
                }
            } else {
                if (event.getEntity().level() instanceof ServerLevel serverLevel) {
                    if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                        City city = CityManager.getInstance().getCityByPlayer(Inhabitant.fromPlayer(serverPlayer));
                        if(city == null) {
                            event.setCanceled(true);
                            return;
                        }
                        CityManager.getInstance().addBuilding(city, event.getPos());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void levelLoad(LevelEvent.@NotNull Load event){
        if(event.getLevel() instanceof ServerLevel serverLevel) {
            CityManager manager = new CityManager(serverLevel);
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onBreakEvent(BlockEvent.BreakEvent event){
        if(event.getState().getBlock() instanceof IBuildingBlock iBuildingBlock) {
            boolean cancel = iBuildingBlock.onBreak(event.getPlayer(), event.getPos());
            event.setCanceled(cancel);
        }
    }
}
