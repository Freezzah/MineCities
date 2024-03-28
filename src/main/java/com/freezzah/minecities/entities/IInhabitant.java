package com.freezzah.minecities.entities;

import com.freezzah.minecities.utils.ITaggable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface IInhabitant extends ITaggable {

    /**
     * Returns the player object that the {@link Inhabitant} is based on
     *
     * @param level {@link Level} to get the player from
     * @return {@link Player} player if exists, otherwise null
     */
    @Nullable Player toPlayer(@NotNull Level level);

    /**
     * Returns UUID of the Inhabitant, equal to {@link Player#getUUID()}
     *
     * @return {@link Player#getUUID()}
     */
    @NotNull UUID getUUID();

    /**
     * Returns Name of the Inhabitant, equal to {@link Player#getName()}
     *
     * @return {@link Player#getName()} ()}
     */
    @NotNull String getName();
}
