package com.freezzah.minecities.blocks.building.registry;

import com.freezzah.minecities.blocks.building.IBuilding;
import com.freezzah.minecities.blocks.building.IBuildingBlock;
import com.freezzah.minecities.city.City;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class BuildingEntry {
    private final ResourceLocation registryName;
    private final IBuildingBlock buildingBlock;
    private final Supplier<IBuilding> buildingProducer;

    public static final class Builder
    {
        private IBuildingBlock buildingBlock;
        private Supplier<IBuilding> buildingProducer;
        private ResourceLocation registryName;

        public <T extends IBuildingBlock> Builder setBuildingBlock(final T buildingBlock)
        {
            this.buildingBlock = buildingBlock;
            return this;
        }

        public Builder setBuildingProducer(final Supplier<IBuilding> buildingProducer)
        {
            this.buildingProducer = buildingProducer;
            return this;
        }

        public Builder setRegistryName(final ResourceLocation registryName)
        {
            this.registryName = registryName;
            return this;
        }

        /**
         * Method used to create the entry.
         *
         * @return The entry.
         */
        @SuppressWarnings("PMD.AccessorClassGeneration") //The builder explicitly allowed to create an instance.
        public BuildingEntry createBuildingEntry()
        {
            Objects.requireNonNull(buildingBlock);
            Objects.requireNonNull(buildingProducer);
            Objects.requireNonNull(registryName);

            return new BuildingEntry(registryName, buildingBlock, buildingProducer);
        }
    }

    private BuildingEntry(final ResourceLocation registryName,
                         final IBuildingBlock buildingBlock,
                         final Supplier<IBuilding> buildingProducer)
    {
        super();
        this.registryName = registryName;
        this.buildingBlock = buildingBlock;
        this.buildingProducer = buildingProducer;
    }

    public IBuilding produceBuilding(final City city)
    {
        final IBuilding building = buildingProducer.get();
        building.setBuildingType(this);
        return building;
    }

    public ResourceLocation getRegistryName()
    {
        return registryName;
    }
}