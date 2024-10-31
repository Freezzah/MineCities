package com.freezzah.minecities.data.texture;


import com.freezzah.minecities.blocks.block.registry.ModBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.freezzah.minecities.Constants.MOD_ID;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        normalBlock(ModBlock.TOWNHALL_BLOCK.get());
        normalBlock(ModBlock.BANK_BLOCK.get());
        normalBlock(ModBlock.HOUSE_BLOCK.get());
        normalBlock(ModBlock.WATER_COLLECTOR_BLOCK.get());
        normalBlock(ModBlock.FARM_BLOCK.get());
        normalBlock(ModBlock.WELL_BLOCK.get());
    }

    protected void normalBlock(Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        simpleBlock(block, models().cubeAll(path, modLoc("block/" + path)));
        simpleBlockItem(block, models().getExistingFile(modLoc("block/" + path)));
    }
}