package dev.aullisia.pmmsc.network.packet;

import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record MinecartMaxSpeedPayload (double speed) implements CustomPayload {
    public static final Identifier MINECART_MAX_SPEED_PAYLOAD_ID = Identifier.of(PerMinecartMaxSpeedCustomiser.MOD_ID, "minecart_max_speed_payload");
    public static final CustomPayload.Id<MinecartMaxSpeedPayload> ID = new CustomPayload.Id<>(MINECART_MAX_SPEED_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, MinecartMaxSpeedPayload> CODEC = PacketCodec.tuple(PacketCodecs.DOUBLE, MinecartMaxSpeedPayload::speed, MinecartMaxSpeedPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
