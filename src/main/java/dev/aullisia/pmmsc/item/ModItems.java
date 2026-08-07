package dev.aullisia.pmmsc.item;

import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import dev.aullisia.pmmsc.component.ModComponents;
import dev.aullisia.pmmsc.item.custom.WrenchItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import java.util.function.Function;

public class ModItems {
    public static final Item WRENCH = registerItem("wrench", WrenchItem::new, new Item.Properties().stacksTo(1).component(ModComponents.TARGET_MINECART, null));

    public static Item registerItem(String path, Function<Item.Properties, Item> factory, Item.Properties settings) {
        final ResourceKey<Item> registryKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(PerMinecartMaxSpeedCustomiser.MOD_ID, path));
        return Items.registerItem(registryKey, factory, settings);
    }

    public static void registerModItems() {
        PerMinecartMaxSpeedCustomiser.LOGGER.info("Registering Mod Items for" + PerMinecartMaxSpeedCustomiser.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(WRENCH);
        });
    }
}
