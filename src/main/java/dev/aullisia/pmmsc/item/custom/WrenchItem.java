package dev.aullisia.pmmsc.item.custom;

import dev.aullisia.pmmsc.component.ModComponents;
import dev.aullisia.pmmsc.network.packet.MinecartMaxSpeedSyncPayload;
import dev.aullisia.pmmsc.screen.MinecartSpeedScreen;
import dev.aullisia.pmmsc.util.CustomMaxSpeedAccessor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;

import java.util.Objects;

public class WrenchItem extends Item {
    public WrenchItem(Settings settings) {
        super(settings);
    }

    public static void useWrench(PlayerEntity player, AbstractMinecartEntity cart, Hand hand) {
        if (player.getWorld().isClient) {
            MinecraftClient.getInstance().setScreen(
                    new MinecartSpeedScreen(Text.of(Objects.requireNonNull(cart.getDisplayName())), cart)
            );
        } else {
            player.getStackInHand(hand).set(ModComponents.TARGET_MINECART, cart.getUuid());
            player.setCurrentHand(hand);

            if (player instanceof ServerPlayerEntity serverPlayer) {
                ServerPlayNetworking.send(serverPlayer,
                        new MinecartMaxSpeedSyncPayload(((CustomMaxSpeedAccessor) cart).getCustomMaxSpeed()));
            }
        }
    }
}
