package dev.aullisia.pmmsc.datagen;

import dev.aullisia.pmmsc.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
//? if >=1.21.4 {
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
//?}
//? if <=1.21.3 {
/*import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
*///?}
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

    //? if >=1.21.4 {
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
    //?}

    //? if <=1.21.3 {
    /*@Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                this.createShaped(RecipeCategory.TOOLS, ModItems.WRENCH, 1)
                        .pattern("gL")
                        .pattern("/ ")
                        .input('g', Items.GOLD_INGOT)
                        .input('L', Items.LEATHER)
                        .input('/', Items.IRON_INGOT)
                        .group("wrench")
                        .criterion("has_gold_ingot", this.conditionsFromItem(Items.GOLD_INGOT))
                        .offerTo(this.exporter);
            }
        };
    }
    *///?}

    @Override
    public String getName() {
        return "PerMinecartMaxSpeedCustomiserRecipeProvider";
    }
}