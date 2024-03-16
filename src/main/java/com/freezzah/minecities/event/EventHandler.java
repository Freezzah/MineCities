package com.freezzah.minecities.event;

import com.freezzah.minecities.blocks.IBuildingBlock;
import com.freezzah.minecities.city.CityManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.jetbrains.annotations.NotNull;

public class EventHandler {

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onPlaceEvent(@NotNull BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity().level() instanceof ServerLevel serverLevel) {
            if (event.getEntity() instanceof ServerPlayer serverPlayer) {
                if (event.getPlacedBlock().getBlock() instanceof IBuildingBlock iBuildingBlock) {
                    boolean cancel = iBuildingBlock.onPlace(serverPlayer, event.getPos());
                    event.setCanceled(cancel);
                }
            }
        }
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onBreakEvent(BlockEvent.@NotNull BreakEvent event){
        if(event.getState().getBlock() instanceof IBuildingBlock iBuildingBlock) {
            boolean cancel = iBuildingBlock.onBreak(event.getPlayer(), event.getPos());
            event.setCanceled(cancel);
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
    public void tickEvent(@NotNull TickEvent.LevelTickEvent levelTickEvent) {
        if (levelTickEvent.side.isServer()) {
            if (levelTickEvent.level.getGameTime() % 100 == 0) {
                CityManager.getInstance().tickSlow(levelTickEvent.level);
            } else {
                CityManager.getInstance().tick(levelTickEvent.level);
            }
        }
    }
}
