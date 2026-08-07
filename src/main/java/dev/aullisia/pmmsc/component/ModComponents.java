package dev.aullisia.pmmsc.component;

import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import dev.aullisia.pmmsc.util.UuidCodec;
import java.util.UUID;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ModComponents {
    public static final DataComponentType<UUID> TARGET_MINECART = Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            ResourceLocation.fromNamespaceAndPath(PerMinecartMaxSpeedCustomiser.MOD_ID, "target_minecart_component"),
            DataComponentType.<UUID>builder().persistent(UuidCodec.UUID_CODEC).build()
    );
    public static void registerModComponents() {
    }
}
