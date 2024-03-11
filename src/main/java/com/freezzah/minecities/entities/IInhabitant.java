package com.freezzah.minecities.entities;

import com.freezzah.minecities.utils.ITaggable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface IInhabitant extends ITaggable {

    @Nullable Player toPlayer(@NotNull Level level);

    @NotNull UUID getUUID();

    @NotNull String getName();
}
