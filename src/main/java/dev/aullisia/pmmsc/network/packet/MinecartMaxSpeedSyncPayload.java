package dev.aullisia.pmmsc.network.packet;

import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MinecartMaxSpeedSyncPayload (double speed) implements CustomPacketPayload {
    public static final ResourceLocation MINECART_MAX_SPEED_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(PerMinecartMaxSpeedCustomiser.MOD_ID, "minecart_max_speed_sync");
    public static final CustomPacketPayload.Type<MinecartMaxSpeedSyncPayload> ID = new CustomPacketPayload.Type<>(MINECART_MAX_SPEED_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MinecartMaxSpeedSyncPayload> CODEC = StreamCodec.composite(ByteBufCodecs.DOUBLE, MinecartMaxSpeedSyncPayload::speed, MinecartMaxSpeedSyncPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}