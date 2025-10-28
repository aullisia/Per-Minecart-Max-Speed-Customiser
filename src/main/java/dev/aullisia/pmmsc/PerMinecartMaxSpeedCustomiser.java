package dev.aullisia.pmmsc;

import dev.aullisia.pmmsc.component.ModComponents;
import dev.aullisia.pmmsc.events.EventHooks;
import dev.aullisia.pmmsc.item.ModItems;
import dev.aullisia.pmmsc.network.ModNetwork;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerMinecartMaxSpeedCustomiser implements ModInitializer {
    public static final String MOD_ID = "pmmsc";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModNetwork.registerServer();
        ModComponents.registerModComponents();
        ModItems.registerModItems();
        EventHooks.hookEvents();
        LOGGER.info("PerMinecartMaxSpeedCustomiser Initialised!");
    }
}