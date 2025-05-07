package dev.aullisia.pmmsc.datagen;

import dev.aullisia.pmmsc.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class PerMinecartMaxSpeedCustomiserRecipeProvider extends FabricRecipeProvider {
    public PerMinecartMaxSpeedCustomiserRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

                createShaped(RecipeCategory.TOOLS, ModItems.WRENCH, 1)
                        .pattern("gL")
                        .pattern("/ ")
                        .input('g', Items.GOLD_INGOT)
                        .input('L', Items.LEATHER)
                        .input('/', Items.IRON_INGOT)
                        .group("wrench")
                        .criterion("has_gold_ingot", conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "PerMinecartMaxSpeedCustomiserRecipeProvider";
    }
}
