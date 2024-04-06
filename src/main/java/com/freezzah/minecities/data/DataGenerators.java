package com.freezzah.minecities.data;

import com.freezzah.minecities.Constants;
import com.freezzah.minecities.data.texture.ModBlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
    public static void gatherData(GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput output = event.getGenerator().getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

            generator.addProvider(true, new ModBlockStateProvider(output, existingFileHelper));
        } catch (RuntimeException e) {
            Constants.LOGGER.error("Failed to generate data", e);
        }
    }
}
