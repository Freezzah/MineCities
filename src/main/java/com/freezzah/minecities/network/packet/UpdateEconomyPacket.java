package com.freezzah.minecities.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

import static com.freezzah.minecities.Constants.MOD_ID;

public record UpdateEconomyPacket(long gold, UUID cityUUID) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(MOD_ID, "updateeconomypacket");

    public UpdateEconomyPacket(final FriendlyByteBuf buffer) {
        this(buffer.readLong(), buffer.readUUID());
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeLong(gold());
        pBuffer.writeUUID(cityUUID());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
