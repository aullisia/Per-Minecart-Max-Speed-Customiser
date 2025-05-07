package dev.aullisia.pmmsc;

import dev.aullisia.pmmsc.datagen.PerMinecartMaxSpeedCustomiserRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PerMinecartMaxSpeedCustomiserDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(PerMinecartMaxSpeedCustomiserRecipeProvider::new);
	}
}
