package dev.aullisia.pmmsc.network;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiserConfig;
import dev.aullisia.pmmsc.component.ModComponents;
import dev.aullisia.pmmsc.network.packet.MinecartMaxSpeedPayload;
import dev.aullisia.pmmsc.network.packet.MinecartMaxSpeedSyncPayload;
import dev.aullisia.pmmsc.screen.MinecartSpeedScreen;
import dev.aullisia.pmmsc.util.CustomMaxSpeedAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//? if <26.1 {
import static net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry.playC2S;
import static net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry.playS2C;
//?} else {
/*import static net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry.clientboundPlay;
import static net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry.serverboundPlay;
 *///?}

public class ModNetwork {
    public static void registerServer() {
        //? if <26.1 {
        playS2C().register(MinecartMaxSpeedSyncPayload.ID, MinecartMaxSpeedSyncPayload.CODEC);
        playC2S().register(MinecartMaxSpeedPayload.ID, MinecartMaxSpeedPayload.CODEC);
        //?} else {
        /*clientboundPlay().register(MinecartMaxSpeedSyncPayload.ID, MinecartMaxSpeedSyncPayload.CODEC);
        serverboundPlay().register(MinecartMaxSpeedPayload.ID, MinecartMaxSpeedPayload.CODEC);
        *///?}

        ServerPlayNetworking.registerGlobalReceiver(MinecartMaxSpeedPayload.ID, (payload, context) -> {
            ServerPlayer player = context.player();
            double speed = payload.speed();

            //? if >=1.21.9 {
            /*Objects.requireNonNull(context.server()).execute(() -> {
             *///?}
            //? if <1.21.9 {
            Objects.requireNonNull(context.player().getServer()).execute(() -> {
                //?}
                var stack = player.getItemInHand(player.getUsedItemHand());
                var cartUuid = stack.get(ModComponents.TARGET_MINECART);
                //? if <1.21.5 {
                Entity cartEntity = player.serverLevel().getEntity(cartUuid);
                //?}
                //? if >=1.21.5 {
                /*Entity cartEntity = player.level().getEntity(cartUuid);
                 *///?}

                if ((cartEntity instanceof AbstractMinecart cart)) {
                    double clampedSpeed = Math.min(Math.max(-1, speed), PerMinecartMaxSpeedCustomiserConfig.minecartMaxSpeed.get());
                    ((CustomMaxSpeedAccessor) cart).setCustomMaxSpeed(clampedSpeed);
                    ServerPlayNetworking.send(player, new MinecartMaxSpeedSyncPayload(speed));
                }
            });
        });
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(MinecartMaxSpeedSyncPayload.ID, (payload, context) -> {
            double syncedSpeed = payload.speed();
            context.client().execute(() -> {
                //? if <26.2 {
                if (Minecraft.getInstance().screen instanceof MinecartSpeedScreen screen) {
                    screen.updateSpeedField(syncedSpeed);
                }
                //?} else {
                /*if (Minecraft.getInstance().gui.screen() instanceof MinecartSpeedScreen screen) {
                    screen.updateSpeedField(syncedSpeed);
                }
                *///?}
            });
        });
    }
}
