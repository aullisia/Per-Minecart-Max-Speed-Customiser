package dev.aullisia.pmmsc.component;

import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import dev.aullisia.pmmsc.util.UuidCodec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class ModComponents {
    public static final ComponentType<UUID> TARGET_MINECART = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(PerMinecartMaxSpeedCustomiser.MOD_ID, "target_minecart_component"),
            ComponentType.<UUID>builder().codec(UuidCodec.UUID_CODEC).build()
    );
    public static void registerModComponents() {
    }
}
