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

public record UpdateWaterPacket(long water, UUID cityUUID) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdateWaterPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "updatewaterpacket"));

    public static final StreamCodec<ByteBuf, UpdateWaterPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.LONG,
            UpdateWaterPacket::water,
            UUIDUtil.STREAM_CODEC,
            UpdateWaterPacket::cityUUID,
            UpdateWaterPacket::new
    );
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
