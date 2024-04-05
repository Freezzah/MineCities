package com.freezzah.minecities.blocks.block;

import com.freezzah.minecities.blocks.building.TileEntityBuilding;
import com.freezzah.minecities.blocks.building.registry.ModBuildingRegistry;
import com.freezzah.minecities.city.City;
import com.freezzah.minecities.city.CityManager;
import com.freezzah.minecities.entities.Inhabitant;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBuildingBlock extends Block implements IBuildingBlock {
    public AbstractBuildingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBreak(@NotNull Player player, @NotNull BlockPos pos) {
        if (player instanceof ServerPlayer serverPlayer) {
            City city = CityManager.getInstance().getCityByPlayer(Inhabitant.fromPlayer(serverPlayer));
            if (city != null) {
                CityManager.getInstance().getCityByBuilding(pos).getBuildingManager().removeBuilding(pos);
                return false;
            } else if (CityManager.getInstance().getCityByBuilding(pos) == null) {
                CityManager.getInstance().removeUnassociatedBuilding(pos);
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onPlace(ServerPlayer serverPlayer, BlockPos pos) {
        City city = CityManager.getInstance().getCityByPlayer(Inhabitant.fromPlayer(serverPlayer));
        if (city != null) {
            city.getBuildingManager().addBuilding(pos, this);
            return false;
        }
        return true;
    }

    public ResourceLocation getBuildingName() {
        return getBuildingType().getRegistryName();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull final BlockPos blockPos, @NotNull final BlockState blockState) {
        final TileEntityBuilding teBuilding = ModBuildingRegistry.building.get().create(blockPos, blockState);
        teBuilding.setBuilding(this);
        teBuilding.registryName = this.getBuildingName();
        return teBuilding;
    }
}
