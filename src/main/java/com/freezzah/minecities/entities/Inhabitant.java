package com.freezzah.minecities.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Inhabitant implements IInhabitant {
    private final UUID uuid;
    private final String name;

    public Inhabitant(@NotNull UUID uuid, @NotNull String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Contract("_ -> new")
    public static @NotNull Inhabitant fromPlayer(@NotNull Player player) {
        return new Inhabitant(player.getUUID(), player.getName().toString());
    }

    @Override
    public @Nullable Player toPlayer(@NotNull Level level) {
        return level.getPlayerByUUID(this.uuid);
    }

    @Override
    public @NotNull UUID getUUID() {
        return this.uuid;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public boolean equals(@NotNull Object obj) {
        if (obj instanceof Inhabitant inhabitant) {
            return inhabitant.getUUID().equals(getUUID());
        }
        if (obj instanceof Player player) {
            return player.getUUID().equals(getUUID());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getName().hashCode() + getUUID().hashCode();
    }

    private static final String UUID = "uuid";
    private static final String USERNAME = "username";

    @Override
    public @NotNull CompoundTag write() {
        CompoundTag tag = new CompoundTag();
        tag.putUUID(UUID, this.getUUID());
        tag.putString(USERNAME, this.getName());
        return tag;
    }

    public static @Nullable Inhabitant load(@NotNull CompoundTag tag) {
        if(!(tag.contains(UUID) && tag.contains(USERNAME))){
            return null;
        }
        UUID uuid = tag.getUUID(UUID);
        String username = tag.getString(USERNAME);
        if(username.isEmpty()) return null;
        return new Inhabitant(uuid, username);
    }
}
