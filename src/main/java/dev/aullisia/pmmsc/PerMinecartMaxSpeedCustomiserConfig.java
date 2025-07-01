package dev.aullisia.pmmsc;

import com.supermartijn642.configlib.ConfigBuilder;
import com.supermartijn642.configlib.ModConfig;
import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.util.function.Supplier;

public class PerMinecartMaxSpeedCustomiserConfig {
    public static final Supplier<Integer> minecartMaxSpeed;

    static {
        if (FabricLoader.getInstance().isModLoaded("supermartijn642configlib")){
            IConfigBuilder builder = ConfigBuilders.newTomlConfig(PerMinecartMaxSpeedCustomiser.MOD_ID, "PerMinecartMaxSpeedCustomiser-Common", false);

            builder.push("General");
            minecartMaxSpeed = builder.comment("The maximum speed for minecarts. Default is 1000, Vanilla game rule default is 8. Must be between 0 and 1000.")
                    .define("minecartMaxSpeed", 1000, 0, 1000);
            builder.pop();

            builder.build();
        } else {
            minecartMaxSpeed = () -> 1000;
            System.out.println("[PerMinecartMaxSpeedCustomiser] SuperMartijn642's Config Lib not found");
        }
    }
}
