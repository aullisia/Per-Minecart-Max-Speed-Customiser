package dev.aullisia.pmmsc.datagen;

import dev.aullisia.pmmsc.item.ModItems;
//? if <26.1 {
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//?} else {
/*import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
 *///?}
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import java.util.concurrent.CompletableFuture;

public class PerMinecartMaxSpeedCustomiserRecipeProvider extends FabricRecipeProvider {
    //? if <26.1 {
    public PerMinecartMaxSpeedCustomiserRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }
    //?} else {
    /*public PerMinecartMaxSpeedCustomiserRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }
    *///?}

    //? if >=1.21.4 {
    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                shaped(RecipeCategory.TOOLS, ModItems.WRENCH, 1)
                        .pattern("g ")
                        .pattern("/L")
                        .define('g', Items.GOLD_INGOT)
                        .define('L', Items.LEATHER)
                        .define('/', Items.STICK)
                        .group("wrench")
                        .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
                        .save(output);
            }
        };
    }
    //?}

    //? if <=1.21.3 {
    /*@Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.TOOLS, ModItems.WRENCH, 1)
                        .pattern("g ")
                        .pattern("/L")
                        .define('g', Items.GOLD_INGOT)
                        .define('L', Items.LEATHER)
                        .define('/', Items.STICK)
                        .group("wrench")
                        .unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
                        .save(exporter, ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath("pmmsc", "wrench")));
            }
        };
    }
    *///?}

    @Override
    public String getName() {
        return "PerMinecartMaxSpeedCustomiserRecipeProvider";
    }
}