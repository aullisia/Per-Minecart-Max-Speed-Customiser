package dev.aullisia.pmmsc.events;

import dev.aullisia.pmmsc.item.ModItems;
import dev.aullisia.pmmsc.item.custom.WrenchItem;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

public class EventHooks {
    public static void hookEvents() {
        // Wrench right click on entity
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hand == InteractionHand.MAIN_HAND && player.isShiftKeyDown() && entity instanceof AbstractMinecart minecart && player.getItemInHand(hand).getItem() == ModItems.WRENCH) {
                WrenchItem.useWrench(player, minecart, hand);
                return InteractionResult.SUCCESS; // Block default chest/furnace behavior
            }

            return InteractionResult.PASS; // Allow normal behavior
        });
    }
}
