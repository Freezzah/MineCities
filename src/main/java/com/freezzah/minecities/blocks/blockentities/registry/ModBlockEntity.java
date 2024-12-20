package com.freezzah.minecities.blocks.blockentities.registry;

import com.freezzah.minecities.blocks.block.registry.ModBlock;
import com.freezzah.minecities.blocks.blockentities.WaterCollectorBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MOD_ID);

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }

    public static final Supplier<BlockEntityType<WaterCollectorBlockEntity>> WATER_COLLECTOR_BLOCK_ENTITY =
            REGISTER.register(ModBlockEntityId.WATER_COLLECTOR_BLOCK_ENTITY_ID,
                    () -> new BlockEntityType<>(WaterCollectorBlockEntity::new, ModBlock.WATER_COLLECTOR_BLOCK.get(), ModBlock.WELL_BLOCK.get()));
}
