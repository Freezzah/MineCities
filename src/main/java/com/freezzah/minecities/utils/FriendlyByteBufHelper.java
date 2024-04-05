package com.freezzah.minecities.utils;

import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public class FriendlyByteBufHelper {
    //TODO this class feels weird, might need to revamp
    private final UUID uuid;
    public FriendlyByteBufHelper(UUID uuid) {
        this.uuid = uuid;
    }
    public void writeUUID(FriendlyByteBuf byteBuf) {
        byteBuf.writeUUID(uuid);
    }
}
