package dev.aullisia.pmmsc.network.packet;

import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record MinecartMaxSpeedSyncPayload (double speed) implements CustomPayload {
    public static final Identifier MINECART_MAX_SPEED_PAYLOAD_ID = Identifier.of(PerMinecartMaxSpeedCustomiser.MOD_ID, "minecart_max_speed_sync");
    public static final CustomPayload.Id<MinecartMaxSpeedSyncPayload> ID = new CustomPayload.Id<>(MINECART_MAX_SPEED_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, MinecartMaxSpeedSyncPayload> CODEC = PacketCodec.tuple(PacketCodecs.DOUBLE, MinecartMaxSpeedSyncPayload::speed, MinecartMaxSpeedSyncPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}