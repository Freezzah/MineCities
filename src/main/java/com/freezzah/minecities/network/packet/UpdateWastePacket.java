package com.freezzah.minecities.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static com.freezzah.minecities.Constants.MOD_ID;

public record UpdateWastePacket(long waste, UUID cityUUID) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(MOD_ID, "updatewastepacket");

    public UpdateWastePacket(final FriendlyByteBuf buffer) {
        this(buffer.readLong(), buffer.readUUID());
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeLong(waste());
        pBuffer.writeUUID(cityUUID());
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}
