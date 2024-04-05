package com.freezzah.minecities.blocks.building;

import com.freezzah.minecities.blocks.block.AbstractBuildingBlock;
import com.freezzah.minecities.blocks.block.IBuildingBlock;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityBuilding extends BlockEntity {
    /**
     * The name of the building location.
     */
    public ResourceLocation registryName;
    private City city;
    private AbstractBuildingBlock building;

    public TileEntityBuilding(BlockPos pPos, BlockState pBlockState) {
        super(ModBuildingRegistry.building.get(), pPos, pBlockState);
    }

    public TileEntityBuilding(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public ResourceLocation getBuildingName() {
        if (registryName != null && !registryName.getPath().isEmpty()) {
            return registryName;
        }
        return getBlockState().getBlock() instanceof IBuildingBlock ?
                ((IBuildingBlock) getBlockState().getBlock()).getBuildingName() : null;
    }

    public City getCity() {
        if (city == null) {
            city = CityManager.getInstance().getCityByBuilding(getBlockPos());
        }
        return city;
    }

    public TileEntityBuilding setCity(City city) {
        this.city = city;
        return this;
    }

    public AbstractBuildingBlock getBuilding() {
        return building;
    }

    public TileEntityBuilding setBuilding(AbstractBuildingBlock building) {
        this.building = building;
        return this;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
    }
}
