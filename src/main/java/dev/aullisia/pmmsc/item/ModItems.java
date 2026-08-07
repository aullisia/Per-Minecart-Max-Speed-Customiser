package dev.aullisia.pmmsc.item;

import dev.aullisia.pmmsc.PerMinecartMaxSpeedCustomiser;
import dev.aullisia.pmmsc.component.ModComponents;
import dev.aullisia.pmmsc.item.custom.WrenchItem;
//? if <26.1 {
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
//?} else {
/*import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
 *///?}
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
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
        //? if <26.1 {
        return Items.registerItem(registryKey, factory, settings);
        //?} else {
        /*Item item = factory.apply(settings.setId(registryKey));
        return Registry.register(BuiltInRegistries.ITEM, registryKey, item);
        *///?}
    }

    public static void registerModItems() {
        PerMinecartMaxSpeedCustomiser.LOGGER.info("Registering Mod Items for" + PerMinecartMaxSpeedCustomiser.MOD_ID);

        //? if <26.1 {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(WRENCH);
        });
        //?} else {
        /*CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.accept(WRENCH);
        });
        *///?}
    }
}
