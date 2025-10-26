package dev.aullisia.pmmsc.network;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiserConfig;
import dev.aullisia.pmmsc.component.ModComponents;
import dev.aullisia.pmmsc.network.packet.MinecartMaxSpeedPayload;
import dev.aullisia.pmmsc.network.packet.MinecartMaxSpeedSyncPayload;
import dev.aullisia.pmmsc.screen.MinecartSpeedScreen;
import dev.aullisia.pmmsc.util.CustomMaxSpeedAccessor;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
//? if <1.21.5 {
import net.minecraft.client.gl.ShaderProgramKeys;
//?}
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry.playC2S;
import static net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry.playS2C;

public class ModNetwork {
    public static void registerModNetwork() {
        playC2S().register(MinecartMaxSpeedPayload.ID, MinecartMaxSpeedPayload.CODEC);
        playS2C().register(MinecartMaxSpeedSyncPayload.ID, MinecartMaxSpeedSyncPayload.CODEC); // <-- fix here

        ServerPlayNetworking.registerGlobalReceiver(MinecartMaxSpeedPayload.ID, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            double speed = payload.speed();

            Objects.requireNonNull(context.player().getServer()).execute(() -> {
                var stack = player.getStackInHand(player.getActiveHand());
                var cartUuid = stack.get(ModComponents.TARGET_MINECART);
                //? if <1.21.5 {
                Entity cartEntity = player.getServerWorld().getEntity(cartUuid);
                //?}
                //? if >=1.21.5 {
                /*Entity cartEntity = player.getWorld().getEntity(cartUuid);
                 *///?}

                PerMinecartMaxSpeedCustomiser.LOGGER.info(cartUuid.toString());

                if ((cartEntity instanceof AbstractMinecartEntity cart)) {
                    double clampedSpeed = Math.min(Math.max(-1, speed), PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get());
                    ((CustomMaxSpeedAccessor) cart).setCustomMaxSpeed(clampedSpeed);
                    ServerPlayNetworking.send(player, new MinecartMaxSpeedSyncPayload(speed));
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(MinecartMaxSpeedSyncPayload.ID, (payload, context) -> {
            double syncedSpeed = payload.speed();
            context.client().execute(() -> {
                if (MinecraftClient.getInstance().currentScreen instanceof MinecartSpeedScreen screen) {
                    screen.updateSpeedField(syncedSpeed);
                }
            });
        });
    }
}
