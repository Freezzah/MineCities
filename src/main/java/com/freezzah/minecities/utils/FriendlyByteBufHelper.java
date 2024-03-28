package com.freezzah.minecities.utils;

import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public class FriendlyByteBufHelper {
    private final UUID uuid;

    public FriendlyByteBufHelper(UUID uuid) {
        this.uuid = uuid;
    }

    public void writeUUID(FriendlyByteBuf byteBuf) {
        byteBuf.writeUUID(uuid);
    }
}
