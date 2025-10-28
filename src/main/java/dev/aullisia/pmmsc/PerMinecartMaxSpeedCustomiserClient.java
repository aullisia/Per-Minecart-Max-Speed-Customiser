package dev.aullisia.pmmsc;

import dev.aullisia.pmmsc.network.ModNetwork;
import net.fabricmc.api.ClientModInitializer;

public class PerMinecartMaxSpeedCustomiserClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModNetwork.registerClient();
    }
}
