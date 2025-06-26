package dev.aullisia.pmmsc;

import dev.aullisia.pmmsc.component.ModComponents;
import dev.aullisia.pmmsc.events.EventHooks;
import dev.aullisia.pmmsc.item.ModItems;
import dev.aullisia.pmmsc.item.custom.WrenchItem;
import dev.aullisia.pmmsc.network.ModNetwork;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerMinecartMaxSpeedCustomiser implements ModInitializer {
    public static final String MOD_ID = "pmmsc";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModNetwork.registerModNetwork();
        ModComponents.registerModComponents();
        ModItems.registerModItems();
        EventHooks.hookEvents();
        LOGGER.info("PerMinecartMaxSpeedCustomiser Initialised!");
    }
}