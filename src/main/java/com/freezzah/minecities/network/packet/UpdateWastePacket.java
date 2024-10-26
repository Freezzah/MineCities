package com.freezzah.minecities.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static com.freezzah.minecities.Constants.MOD_ID;

public record UpdateWastePacket(long waste, UUID cityUUID) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdateWastePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "updatewastepacket"));

    public static final StreamCodec<ByteBuf, UpdateWastePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.LONG,
            UpdateWastePacket::waste,
            UUIDUtil.STREAM_CODEC,
            UpdateWastePacket::cityUUID,
            UpdateWastePacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
