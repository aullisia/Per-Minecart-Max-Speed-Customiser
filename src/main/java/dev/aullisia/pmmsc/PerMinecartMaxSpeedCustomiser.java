package dev.aullisia.pmmsc;

import dev.aullisia.pmmsc.item.ModItems;
import dev.aullisia.pmmsc.item.custom.WrenchItem;
import dev.aullisia.pmmsc.network.ModNetwork;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerMinecartMaxSpeedCustomiser implements ModInitializer {
	public static final String MOD_ID = "pmmsc";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModNetwork.registerModNetwork();
		ModItems.registerModItems();
		LOGGER.info("PerMinecartMaxSpeedCustomiser Initialised!");
	}
}