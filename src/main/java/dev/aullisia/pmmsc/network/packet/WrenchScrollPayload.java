package dev.aullisia.pmmsc.network.packet;

import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record WrenchScrollPayload(double scrollAmount) implements CustomPayload {
    public static final Identifier WRENCH_SCROLL_PAYLOAD_ID = Identifier.of(PerMinecartMaxSpeedCustomiser.MOD_ID, "wrench_scroll");
    public static final CustomPayload.Id<WrenchScrollPayload> ID = new CustomPayload.Id<>(WRENCH_SCROLL_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, WrenchScrollPayload> CODEC = PacketCodec.tuple(PacketCodecs.DOUBLE, WrenchScrollPayload::scrollAmount, WrenchScrollPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
