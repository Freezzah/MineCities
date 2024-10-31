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

public record UpdateEconomyPacket(long gold, UUID cityUUID) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<UpdateEconomyPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "updateeconomypacket"));

    public static final StreamCodec<ByteBuf, UpdateEconomyPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.LONG,
            UpdateEconomyPacket::gold,
            UUIDUtil.STREAM_CODEC,
            UpdateEconomyPacket::cityUUID,
            UpdateEconomyPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
