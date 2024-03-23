package com.freezzah.minecities.blocks.registry;

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

    public static final Supplier<BlockEntityType<WaterCollectorBlockEntity>> WATER_COLLECTOR_BLOCK_ENTITY =
            REGISTER.register(ModBlockEntityId.WATER_COLLECTOR_BLOCK_ENTITY_ID,
                    () -> BlockEntityType.Builder.of(WaterCollectorBlockEntity::new, ModBlock.WATER_COLLECTOR_BLOCK.get()).build(null));

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
