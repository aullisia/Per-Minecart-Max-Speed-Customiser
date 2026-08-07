package dev.aullisia.pmmsc.item.custom;

import dev.aullisia.pmmsc.component.ModComponents;
import dev.aullisia.pmmsc.network.packet.MinecartMaxSpeedSyncPayload;
import dev.aullisia.pmmsc.screen.MinecartSpeedScreen;
import dev.aullisia.pmmsc.util.CustomMaxSpeedAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import java.util.Objects;

public class WrenchItem extends Item {
    public WrenchItem(Properties settings) {
        super(settings);
    }

    public static void useWrench(Player player, AbstractMinecart cart, InteractionHand hand) {
        var world = player.level();
        if (world.isClientSide()) {
            openMinecartSpeedScreen(cart);
        } else {
            player.getItemInHand(hand).set(ModComponents.TARGET_MINECART, cart.getUUID());
            player.startUsingItem(hand);

            if (player instanceof ServerPlayer serverPlayer) {
                ServerPlayNetworking.send(serverPlayer,
                        new MinecartMaxSpeedSyncPayload(((CustomMaxSpeedAccessor) cart).getCustomMaxSpeed()));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private static void openMinecartSpeedScreen(AbstractMinecart cart) {
//? if <26.2 {
        Minecraft.getInstance().setScreen(
                new MinecartSpeedScreen(Component.translationArg(Objects.requireNonNull(cart.getDisplayName())), cart)
        );
        //?} else {
        /*Minecraft.getInstance().gui.setScreen(
                new MinecartSpeedScreen(Component.translationArg(Objects.requireNonNull(cart.getDisplayName())), cart)
        );
        *///?}
    }
}
