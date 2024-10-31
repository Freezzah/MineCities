package com.freezzah.minecities.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class FriendlyByteBufHelper {
    //TODO this class feels weird, might need to revamp
    private final UUID uuid;
    private BlockPos pos = null;

    public FriendlyByteBufHelper(@NotNull UUID uuid) {
        this.uuid = uuid;
    }

    public FriendlyByteBufHelper(@NotNull UUID uuid, @NotNull BlockPos pos) {
        this.uuid = uuid;
        this.pos = pos;
    }

    public void writeUUID(@NotNull FriendlyByteBuf byteBuf) {
        byteBuf.writeUUID(uuid);
    }

    public void writeBlockPos(@NotNull FriendlyByteBuf byteBuf) {
        if (pos != null)
            byteBuf.writeBlockPos(pos);
    }

    public void writeUUIDAndBlockPos(@NotNull RegistryFriendlyByteBuf registryFriendlyByteBuf) {
        writeUUID(registryFriendlyByteBuf);
        writeBlockPos(registryFriendlyByteBuf);
    }
}
