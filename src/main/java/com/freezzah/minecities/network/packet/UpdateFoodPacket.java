package com.freezzah.minecities.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static com.freezzah.minecities.Constants.MOD_ID;

public record UpdateFoodPacket(double food, UUID cityUUID) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(MOD_ID, "updatefoodpacket");

    public UpdateFoodPacket(final FriendlyByteBuf buffer){
        this(buffer.readDouble(), buffer.readUUID());
    }

    @Override
    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeDouble(food());
        pBuffer.writeUUID(cityUUID());
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}
