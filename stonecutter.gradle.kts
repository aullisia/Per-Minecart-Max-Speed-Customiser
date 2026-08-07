plugins {
    id("dev.kikugie.stonecutter")
}

stonecutter active "1.21.4"

// See https://stonecutter.kikugie.dev/wiki/config/params
stonecutter parameters {
    swaps["mod_version"] = "\"${property("mod.version")}\";"
    swaps["minecraft"] = "\"${node.metadata.version}\";"
    constants["release"] = property("mod.id") != "pmmsc"
    dependencies["fapi"] = node.project.property("deps.fabric_api") as String

    replacements {
        string(current.parsed >= "1.21.11") {
            replace("ResourceLocation", "Identifier")
            replace(
                "net.minecraft.world.entity.vehicle.AbstractMinecart",
                "net.minecraft.world.entity.vehicle.minecart.AbstractMinecart"
            )
            replace(
                "net.minecraft.world.entity.vehicle.MinecartBehavior",
                "net.minecraft.world.entity.vehicle.minecart.MinecartBehavior"
            )
            replace(
                "net.minecraft.world.entity.vehicle.NewMinecartBehavior",
                "net.minecraft.world.entity.vehicle.minecart.NewMinecartBehavior"
            )
            replace("net.minecraft.world.level.GameRules", "net.minecraft.world.level.gamerules.GameRules")
        }
    }
}