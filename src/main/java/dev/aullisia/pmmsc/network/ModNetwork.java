package dev.aullisia.pmmsc.network;

import dev.aullisia.pmmsc.network.packet.WrenchScrollPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry.playC2S;

public class ModNetwork {
    public static final Map<UUID, Double> WRENCH_SCROLL_VALUES = new ConcurrentHashMap<>();

    public static void registerModNetwork(){
        playC2S().register(WrenchScrollPayload.ID, WrenchScrollPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(WrenchScrollPayload.ID, (payload, context) -> {
            double scroll = payload.scrollAmount();

            Objects.requireNonNull(context.player().getServer()).execute(() -> {
                UUID uuid = context.player().getUuid();
                WRENCH_SCROLL_VALUES.put(uuid, scroll);
            });
        });
    }
}
