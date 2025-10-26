package dev.aullisia.pmmsc.events;

import dev.aullisia.pmmsc.item.ModItems;
import dev.aullisia.pmmsc.item.custom.WrenchItem;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class EventHooks {
    public static void hookEvents() {
        // Wrench right click on entity
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (hand == Hand.MAIN_HAND && player.isSneaking() && entity instanceof AbstractMinecartEntity minecart && player.getStackInHand(hand).getItem() == ModItems.WRENCH) {
                WrenchItem.useWrench(player, minecart, hand);
                return ActionResult.SUCCESS; // Block default chest/furnace behavior
            }

            return ActionResult.PASS; // Allow normal behavior
        });
    }
}
