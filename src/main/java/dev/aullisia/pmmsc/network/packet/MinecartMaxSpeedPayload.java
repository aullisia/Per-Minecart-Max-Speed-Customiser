package dev.aullisia.pmmsc.network.packet;

import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record MinecartMaxSpeedPayload (double speed) implements CustomPacketPayload {
    public static final ResourceLocation MINECART_MAX_SPEED_PAYLOAD_ID = ResourceLocation.fromNamespaceAndPath(PerMinecartMaxSpeedCustomiser.MOD_ID, "minecart_max_speed_payload");
    public static final CustomPacketPayload.Type<MinecartMaxSpeedPayload> ID = new CustomPacketPayload.Type<>(MINECART_MAX_SPEED_PAYLOAD_ID);
    public static final StreamCodec<RegistryFriendlyByteBuf, MinecartMaxSpeedPayload> CODEC = StreamCodec.composite(ByteBufCodecs.DOUBLE, MinecartMaxSpeedPayload::speed, MinecartMaxSpeedPayload::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
